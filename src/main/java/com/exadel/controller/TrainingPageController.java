package com.exadel.controller;



import com.exadel.dto.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.exadel.model.entity.events.Event;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.exadel.model.entity.ParticipationStatus;
import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.training.*;
import com.exadel.model.entity.user.User;
import com.exadel.model.entity.user.UserRole;
import com.exadel.service.*;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import com.exadel.util.UserUtil;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/training")
public class TrainingPageController {

    @Autowired
    private TrainingFeedbackService trainingFeedbackService;
    @Autowired
    private EntryService entryService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainingEventService trainingEventService;
    @Autowired
    private TrainingFeedbackEventService trainingFeedbackEventService;
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    EmailMessages emailMessages;

    @Autowired
    private ParticipationService participationService;
    @Autowired
    private ReserveService reserveService;

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public TrainingDTO getTrainingInfo(@RequestParam String trainingId) {
        return new TrainingDTO(trainingService.getTrainingById(trainingId));
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/trainer", method = RequestMethod.GET)
    public UserDTO getTrainer(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        return new UserDTO(training.getTrainer());
    }

    @PreAuthorize("@trainerControlBean.isCoach(#trainingId) or hasRole('0')")
    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    public List<UserDTO> getParticipants(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : training.getParticipants()) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }


    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/check_participation", method = RequestMethod.GET)
    public ParticipationStatus checkParticipation(@RequestParam String userId, @RequestParam String trainingId) {
        return trainingService.checkParticipation(userId, trainingId);
    }

    @RequestMapping(value = "/participation", method = RequestMethod.GET)
    public List<ParticipationDTO> getParticipationTime(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        List<Participation> participations = participationService.getAllParticipationsByTrainingId(training.getId());
        List<ParticipationDTO> participationDTOs = new ArrayList<>();

        for (Participation participation : participations) {
            participationDTOs.add(new ParticipationDTO(participation));
        }
        return participationDTOs;
    }

    @RequestMapping(value = "/waitList", method = RequestMethod.GET)
    public List<UserDTO> getWaitList(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        List<Reserve> reserves = reserveService.getAllReservesByTrainingId(training.getId());
        List<UserDTO> userDTOs = new ArrayList<>();

        for (Reserve reserve : reserves) {
            userDTOs.add(new UserDTO(userService.getUserById(reserve.getId())));
        }
        return userDTOs;
    }

    @PreAuthorize("hasAnyRole('0','1')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public synchronized ParticipationStatus register(@RequestParam String userId,
                                                     @RequestParam String trainingId) {

        Training training = trainingService.getTrainingById(trainingId);
        User visitor = userService.getUserById(userId);
        List<User> participants = training.getParticipants();

        if (participants.size() > training.getMembersCountMax()) {
            Reserve reservist = new Reserve(training, visitor);
            reserveService.addReserve(reservist);
            training.getReserves().add(reservist);

            smtpMailSender.send(userService.getUserById(userId).getEmail(), "Registration",
                    emailMessages.register(visitor, entryService.findNextEntryByTrainingId(new Date(), Long.parseLong(trainingId)), ParticipationStatus.RESERVE));
            return ParticipationStatus.RESERVE;
        } else {
            training.addParticipant(visitor);
            participationService.addParticipation(new Participation(visitor, training, new Date(), null));

            smtpMailSender.send(userService.getUserById(userId).getEmail(), "Registration",
                    emailMessages.register(visitor, entryService.findNextEntryByTrainingId(new Date(), Long.parseLong(trainingId)), ParticipationStatus.MEMBER));
            return ParticipationStatus.MEMBER;
        }
    }

    @PreAuthorize("@userControlBean.isMyAccount(#userId) and hasAnyRole('0','1','2') and @visitControlBean.isStarted(#trainingId,#isRepeated)")
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public synchronized void unregister(@RequestParam String userId, @RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        User visitor = userService.getUserById(userId);
        Participation participation = participationService.getParticipationByTrainingAndUserId(
                training.getId(), visitor.getId());

        if (training.isRepeated()) {
            participation.setEndDay(new Date());
        } else {
            participationService.deleteParticipation(participation.getId());
        }

        Reserve reserve = reserveService.getNextReserveByTrainingId(training.getId());
        /*if (reserve != null) {
            reserveService.deleteReserve(reserve.getId());
            register(String.valueOf(reserve.getReservist().getId()),
                    String.valueOf(reserve.getTraining().getId()));
        }*/
        smtpMailSender.send(reserve.getReservist().getEmail(), "Training",
                emailMessages.becomeMember(reserve.getReservist(), entryService.findNextEntryByTrainingId(new Date(), training.getId())));
    }

    public List<EventDTO> getEvents() {
        List<EventDTO> list = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        events.addAll(trainingEventService.getUnwatchedEvents());
        events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
        events.addAll(userFeedbackEventService.getUnwatchedEvents());
        for (Event event: events){
            list.add(event.toEventDTO());
        }
        return list;
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/register_visitor", method = RequestMethod.POST)
    public void registerByAdmin(@RequestParam String userId, @RequestParam String trainingId) {
        register(userId, trainingId);
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/register_new_visitor", method = RequestMethod.POST)
    public void registerNewExternalByAdmin(@RequestBody UserDTO visitorDTO, @RequestParam String trainingId) {
        userService.addUser(new User(visitorDTO));
        register(String.valueOf(visitorDTO.getId()), trainingId);
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/rating", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public double addTrainingRating(@RequestBody RatingDTO ratingDTO) {
        String trainingId = String.valueOf(ratingDTO.getTrainingId());
        ratingService.addRating(new Rating(trainingService.getTrainingById(trainingId),
                userService.getUserById(String.valueOf(ratingDTO.getUserId()))));
        return trainingService.addRating(ratingDTO.getRating(), trainingId);
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    public List<TrainingFeedbackDTO> getFeedbacks(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        List<TrainingFeedback> feedbacks = (List<TrainingFeedback>)
                trainingFeedbackService.getTrainingFeedbacksByTrainingId(training.getId());
        List<TrainingFeedbackDTO> feedbackDTOs = new ArrayList<>();

        for (TrainingFeedback feedback : feedbacks) {
            feedbackDTOs.add(new TrainingFeedbackDTO(feedback));
        }
        return feedbackDTOs;
    }

    @PreAuthorize("@visitControlBean.isVisiting(#feedbackDTO) and hasAnyRole('0','1','2')")
    @RequestMapping(value = "/newFeedback", method = RequestMethod.POST)
    public void createFeedback(@RequestBody TrainingFeedbackDTO feedbackDTO) {
        TrainingFeedback feedback = new TrainingFeedback(feedbackDTO);
        trainingFeedbackService.addTrainingFeedback(feedback);
        feedbackDTO.setId(feedback.getId());
        trainingFeedbackEventService.addEvent(new TrainingFeedbackEvent(feedbackDTO));
        for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
            entry.getKey().setResult(getEvents());
        }
    }



    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/feedbacks", method = RequestMethod.DELETE)   //called only by ADMIN
    public void deleteFeedback(@RequestParam String feedbackId) {
        trainingFeedbackEventService.deleteByTrainingFeedbackId(Long.parseLong(feedbackId));
        trainingFeedbackService.deleteById(Long.parseLong(feedbackId));

        TrainingFeedback feedback = trainingFeedbackService.getTrainingFeedbackById(Long.parseLong(feedbackId)).get();
        smtpMailSender.send(feedback.getFeedbacker().getEmail(), "Feedbacks", emailMessages.deleteFeedback(feedback));
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public Entry getEntry(@RequestParam String entryId) {
        long id = Long.parseLong(entryId);
        Entry entry = entryService.getEntryById(id);
        System.out.println(entry);
        return entry;
    }

    @PreAuthorize("@trainerControlBean.isCoach(#trainingId) or hasRole('0') or @visitControlBean.isVisit(#trainingId) and hasAnyRole('1','2')")
    @RequestMapping(value = "attachments", method = RequestMethod.GET)
    public List<AttachmentDTO> getAttachments(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        List<Attachment> attachments = attachmentService.getAllAttachmentsByTrainingId(training.getId());
        List<AttachmentDTO> attachmentDTOs = new ArrayList<>();

        for (Attachment attachment : attachments) {
            attachmentDTOs.add(new AttachmentDTO(attachment));
        }
        return attachmentDTOs;
    }

    @PreAuthorize("@trainerControlBean.isCoach(#attachmentDTO) or hasRole('0') or @visitControlBean.isVisit(#attachmentDTO) and hasAnyRole('1','2')")
    @RequestMapping(value = "newAttachment", method = RequestMethod.POST)
    public void createAttachment(@RequestBody AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment(attachmentDTO);
        attachment.setTraining(trainingService.getTrainingById(attachmentDTO.getTrainingId()));
        attachmentService.addAttachment(attachment);
    }

    @PreAuthorize("@trainerControlBean.isCoach(#attachmentDTO) or hasRole('0') or @visitControlBean.isVisit(#attachmentDTO) and hasAnyRole('1','2')")
    @RequestMapping(value = "editAttachment", method = RequestMethod.PUT)
    public void editAttachment(@RequestBody AttachmentDTO attachmentDTO) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentDTO.getId());
        attachment.setTraining(trainingService.getTrainingById(attachmentDTO.getTrainingId()));
        attachmentService.addAttachment(attachment);
    }

    @PreAuthorize("@trainerControlBean.isCoach(#trainingDTO) or hasRole('0')")
    @RequestMapping(method = RequestMethod.PUT)
    public void modifyTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = trainingService.getTrainingById(trainingDTO.getId());
        training.updateTraining(trainingDTO);
        if (UserUtil.hasRole(0)) {
            smtpMailSender.sendToUsers(training.getParticipants(), "Trainings", emailMessages.modifyTraining(training));
        }
        else {
            training.setStatus(TrainingStatus.DRAFTED);

            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in trainings", trainingDTO.getEventDescription());
            for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
                entry.getKey().setResult(getEvents());
            }
        }
        trainingService.updateTraining(training);
    }

    @PreAuthorize("@trainerControlBean.isCoach(#id) or hasRole('0')")
    @RequestMapping(method = RequestMethod.DELETE)
    public void cancelTraining(@RequestParam String id) {
        Training training = trainingService.getTrainingById(Long.parseLong(id));
        training.setStatus(TrainingStatus.CANCELLED);

        if (UserUtil.hasRole(0)) {
            trainingService.cancelById(id);
            smtpMailSender.sendToUsers(training.getParticipants(), "Trainings", emailMessages.deleteTraining(training));
        }
        else {
            TrainingDTO trainingDTO = new TrainingDTO(training);
            trainingDTO.setEventDescription(emailMessages.deleteTrainingToAdmin(training));
            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in trainings", emailMessages.deleteTrainingToAdmin(training));
            for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
                entry.getKey().setResult(getEvents());

            }
        }
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value="/userFeedback",method = RequestMethod.GET)
    public void AskUserFeedback(@RequestParam String userId, @RequestParam String trainingId) {
        User user = userService.getUserById(userId);
        Training training = trainingService.getTrainingById(trainingId);
        smtpMailSender.send(training.getTrainer().getEmail(), "Feedback", emailMessages.askFeedback(training, user));
    }
}

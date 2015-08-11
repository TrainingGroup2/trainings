package com.exadel.controller;


import com.exadel.dto.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.exadel.model.entity.events.Event;
import com.exadel.repository.TrainingFeedbackRepository;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.exadel.model.entity.user.ExternalVisitor;
import com.exadel.model.entity.user.User;
import com.exadel.model.entity.user.UserRole;
import com.exadel.service.*;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import com.exadel.util.UserUtil;
import org.springframework.web.context.request.async.DeferredResult;

import javax.crypto.Cipher;

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
    private SmtpMailSender smtpMailSender;
    @Autowired
    private EmailMessages emailMessages;
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

    @PreAuthorize("@trainerControlBean.isTrainer(#trainingId) or hasRole('0')")
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
        List<Reserve> reserves = reserveService.getAllReservesByTrainingId(Long.parseLong(trainingId));
        List<UserDTO> userDTOs = new ArrayList<>();

        for (Reserve reserve : reserves) {
            userDTOs.add(new UserDTO(userService.getUserById(reserve.getReservist().getId())));
        }
        return userDTOs;
    }

    @PreAuthorize("hasAnyRole('0','1')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public synchronized ParticipationStatus register(@RequestParam String userId, @RequestParam String trainingId) {

        Training training = trainingService.getTrainingById(trainingId);
        User visitor = userService.getUserById(userId);
        List<User> participants = training.getParticipants();

        int participantsCount = participants.size();
        participantsCount -= participationService.countCompletedParticipants(training.getId());

        if (participantsCount < training.getMembersCountMax()
                && reserveService.getNextReserveByTrainingId(training.getId()) == null) {
            participationService.addParticipation(new Participation(visitor, training, new Date(), null));

            smtpMailSender.send(userService.getUserById(userId).getEmail(), "Registration",
                    emailMessages.register(visitor, entryService.findNextEntryByTrainingId(new Date(), Long.parseLong(trainingId)), ParticipationStatus.MEMBER));
            return ParticipationStatus.MEMBER;

        } else {
            Reserve reservist = new Reserve(training, visitor);
            reserveService.addReserve(reservist);

            smtpMailSender.send(userService.getUserById(userId).getEmail(), "Registration",
                    emailMessages.register(visitor, entryService.findNextEntryByTrainingId(new Date(), Long.parseLong(trainingId)), ParticipationStatus.RESERVE));
            return ParticipationStatus.RESERVE;
        }
    }

    @PreAuthorize("@userControlBean.isMyAccount(#userId) and hasAnyRole('0','1','2') and @visitControlBean.isStarted(#trainingId)")
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public synchronized void unregister(@RequestParam String userId, @RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        User visitor = userService.getUserById(userId);

        if (training.isParticipant(visitor.getId())) {
            Participation participation = participationService.getParticipationByTrainingAndUserId(training.getId(), visitor.getId());
            List<Entry> entries = training.getEntries();

            if (training.isRepeated() && new Date().after(entries.get(0).getBeginTime())) {
                participation.setEndDay(new Date());
            } else {
                participationService.deleteParticipation(participation.getId());
            }

            Reserve reserve = reserveService.getNextReserveByTrainingId(training.getId());
            if (reserve != null) {
                smtpMailSender.send(reserve.getReservist().getEmail(), "Training",
                        emailMessages.becomeMember(reserve.getReservist(), entryService.findNextEntryByTrainingId(new Date(), training.getId())));
            }
        } else if (training.isReservist(visitor.getId())) {
            Reserve reserve = reserveService.getReserveByTrainingIdAndUserId(training.getId(), visitor.getId());
            training.getReserves().remove(reserve);
            reserveService.deleteReserve(reserve);
        }
    }

    @RequestMapping(value = "/cancel_participation", method = RequestMethod.POST)
    public synchronized String cancelParticipation(@RequestParam String userId, @RequestParam String trainingId) {
        String trainingID = EmailMessages.doCrypto(Cipher.DECRYPT_MODE, trainingId);
        String userID = EmailMessages.doCrypto(Cipher.DECRYPT_MODE, userId);
        unregister(userID, trainingID);
        return "You have canceled participation";
    }

    @RequestMapping(value = "/confirm_participation", method = RequestMethod.POST)
    public synchronized String becomeMember(@RequestParam String userId, @RequestParam String trainingId) {
        String trainingID = EmailMessages.doCrypto(Cipher.DECRYPT_MODE, trainingId);
        String userID = EmailMessages.doCrypto(Cipher.DECRYPT_MODE, userId);

        Training training = trainingService.getTrainingById(trainingID);
        Reserve reserve = reserveService.getReserveByTrainingIdAndUserId(Long.parseLong(trainingID), Long.parseLong(userID));

        if (training.getParticipants().size() < training.getMembersCountMax()) {
            User visitor = reserve.getReservist();
            training.getReserves().remove(reserve);
            reserveService.deleteReserve(reserve.getId());
            participationService.addParticipation(new Participation(visitor, training, new Date(), null));

            smtpMailSender.send(userService.getUserById(userID).getEmail(), "Registration",
                    emailMessages.register(visitor, entryService.findNextEntryByTrainingId(new Date(), Long.parseLong(trainingID)), ParticipationStatus.MEMBER));
            return "Congratulations! You have become a participant of training.";
        }
        else{
            smtpMailSender.send(userService.getUserById(userID).getEmail(), "Registration",
                    emailMessages.register(reserve.getReservist(), entryService.findNextEntryByTrainingId(new Date(), Long.parseLong(trainingID)), ParticipationStatus.RESERVE));
            return "Unfortunately, all the seats on training are occupied, so you added to reserve.";
        }
    }

    public List<EventDTO> getEvents() {
        List<EventDTO> list = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        events.addAll(trainingEventService.getUnwatchedEvents());
        events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
        events.addAll(userFeedbackEventService.getUnwatchedEvents());
        for (Event event : events) {
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
        User user = new ExternalVisitor(visitorDTO);
        user = userService.addUser(user);
        register(String.valueOf(user.getId()), trainingId);
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
    public TrainingDTO createFeedback(@RequestBody TrainingFeedbackDTO feedbackDTO) {
        TrainingFeedback feedback = new TrainingFeedback(feedbackDTO);
        feedback.setTraining(trainingService.getTrainingById(String.valueOf(feedbackDTO.getTrainingId())));

        long id = trainingFeedbackService.addTrainingFeedback(feedback);

        feedbackDTO.setId(id);
        trainingFeedbackEventService.addEvent(new TrainingFeedbackEvent(feedbackDTO));
        for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
            entry.getKey().setResult(getEvents());
        }

        ratingService.addRating(new Rating(feedback.getTraining(), feedback.getFeedbacker()));
        Training training = feedback.getTraining();
        training.addRating(feedback.getEffectiveness());
        trainingService.updateTraining(training);
        return new TrainingDTO(training);
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/feedbacks", method = RequestMethod.DELETE)   //called only by ADMIN
    public void deleteFeedback(@RequestParam String feedbackId) {
        TrainingFeedback feedback = trainingFeedbackService.getTrainingFeedbackById(Long.parseLong(feedbackId)).get();
        trainingFeedbackEventService.deleteByTrainingFeedbackId(Long.parseLong(feedbackId));
        trainingFeedbackService.deleteById(Long.parseLong(feedbackId));

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

    @PreAuthorize("hasRole('0') or @trainerControlBean.isTrainer(#trainingDTO)")
    @RequestMapping(method = RequestMethod.PUT)
    public void modifyTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = trainingService.getTrainingById(trainingDTO.getId());
        training.updateTraining(trainingDTO);
        if (UserUtil.hasRole(0)) {
            smtpMailSender.sendToUsers(training.getParticipants(), "Trainings", emailMessages.modifyTraining(training));
        } else {
            training.setStatus(TrainingStatus.DRAFTED);

            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in trainings", trainingDTO.getEventDescription());
            for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
                entry.getKey().setResult(getEvents());
            }
        }
        trainingService.updateTraining(training);
    }

    @PreAuthorize("@trainerControlBean.isTrainer(#trainingId) or hasRole('0')")
    @RequestMapping(method = RequestMethod.DELETE)
    public void cancelTraining(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        training.setStatus(TrainingStatus.CANCELLED);

        if (UserUtil.hasRole(0)) {
            trainingService.cancelById(training.getId());
            smtpMailSender.sendToUsers(training.getParticipants(), "Trainings", emailMessages.deleteTraining(training));
        } else {
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
    @RequestMapping(value = "/userFeedback", method = RequestMethod.POST)
    public void askUserFeedback(@RequestParam String userId, @RequestParam String trainingId) {
        User user = userService.getUserById(userId);
        Training training = trainingService.getTrainingById(trainingId);
        smtpMailSender.send(training.getTrainer().getEmail(), "Feedback", emailMessages.askFeedback(training, user));
    }

    @Autowired
    TrainingFeedbackRepository trainingFeedbackRepository;
    @RequestMapping("/byFeedback")
    public TrainingDTO getTrainingId(@RequestParam String id)
    {
        TrainingFeedback feedback=trainingFeedbackRepository.findById(Long.parseLong(id));
        TrainingDTO trainingDTO = new TrainingDTO(feedback.getTraining());
        return trainingDTO;
    }
}

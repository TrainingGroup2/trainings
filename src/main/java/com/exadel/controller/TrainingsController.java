package com.exadel.controller;

import com.exadel.dto.EntryDTO;
import com.exadel.dto.EventDTO;
import com.exadel.dto.TrainingDTO;
import com.exadel.dto.UserDTO;
import com.exadel.model.entity.events.Event;
import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.model.entity.user.UserRole;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import com.exadel.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;

import static com.exadel.Utils.addWeekToDate;
import static com.exadel.Utils.getDayNumberToAdd;

@RestController
@PreAuthorize("hasAnyRole('0','1','2')")
@RequestMapping(value = "/trainings", headers = "Accept=application/json")
public class TrainingsController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainingEventService trainingEventService;
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    private EmailMessages emailMessages;
    @Autowired
    private TrainingFeedbackEventService trainingFeedbackEventService;
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingDTO> getTrainings() {
        List<Training> trainings = (List<Training>) trainingService.getAllTrainings();
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value ="/pages/{pageNumber}",method = RequestMethod.GET)
    public List<TrainingDTO> getTrainingsPaging(@PathVariable Integer pageNumber, @RequestParam Integer size) {
        Page<Training> page = trainingService.getTrainings(pageNumber, size);
        List<Training> trainings = page.getContent();
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value ="/pages/{pageNumber}",method = RequestMethod.GET)
    public List<TrainingDTO> getTrainingsPaging(@PathVariable Integer pageNumber, @RequestParam Integer size) {
        Page<Training> page = trainingService.getTrainings(pageNumber, size);
        List<Training> trainings = page.getContent();
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }
    @RequestMapping(value = "/pages/count/{pageNumber}", method = RequestMethod.GET)
    public Integer getCount(@PathVariable Integer pageNumber, @RequestParam Integer size) {
        Page<Training> page = trainingService.getTrainings(pageNumber, size);
        Integer count = page.getTotalPages();
        return count;
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)   //called only by ADMIN
    public Training createTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = new Training(trainingDTO);
        if (UserUtil.hasRole(0)) {
            training.setStatus(TrainingStatus.APPROVED);
            training = trainingService.addTraining(training);
        }
        else {
            training.setStatus(TrainingStatus.DRAFTED);
            training = trainingService.addTraining(training);
            trainingDTO.setId(training.getId());
            trainingDTO.setEventDescription(emailMessages.newTrainingToAdmin(training));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in Trainings", emailMessages.newTrainingToAdmin(training));
            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
            List<EventDTO> eventDTOs = new ArrayList<>();

            List<Event> events = new ArrayList<>();
            events.addAll(trainingEventService.getUnwatchedEvents());
            events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
            events.addAll(userFeedbackEventService.getUnwatchedEvents());

            for (Event event: events){
                eventDTOs.add(event.toEventDTO());
            }
            for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
                entry.getKey().setResult(eventDTOs);
            }
        }

        if (trainingDTO.isRepeated()) {
            generateEntries(trainingDTO.getBegin(), trainingDTO.getEnd(),
                    training, trainingDTO.getEntries());
        } else {
            List<EntryDTO> entryDTOs = trainingDTO.getEntries();
            Collections.sort(entryDTOs);
            for (EntryDTO entryDTO : entryDTOs) {
                Entry entry = new Entry(entryDTO);
                entry.setTraining(training);
                entryService.addEntry(entry);
            }
        }

        return training;
    }

    void generateEntries(Date beginDay, Date endDay, Training training, List<EntryDTO> entryDTOs) {
        if (beginDay == null || endDay == null)
            return;

        Calendar begin = new GregorianCalendar();
        begin.setTime(beginDay);
        int beginDayOfWeek = begin.get(Calendar.DAY_OF_WEEK);

        Calendar generator = new GregorianCalendar();

        for (EntryDTO entryDTO : entryDTOs) {
            generateTime(generator, entryDTO.getBeginTime(), begin);
            generator.add(Calendar.DATE, getDayNumberToAdd(beginDayOfWeek, entryDTO.getDayOfWeek()));
            entryDTO.setBeginTime(generator.getTime());

            generateTime(generator, entryDTO.getEndTime(), begin);
            generator.add(Calendar.DATE, getDayNumberToAdd(beginDayOfWeek, entryDTO.getDayOfWeek()));
            entryDTO.setEndTime(generator.getTime());
        }

        Collections.sort(entryDTOs);
        Queue<EntryDTO> duplicates =  new LinkedList<>(entryDTOs);
        EntryDTO entryDTO = duplicates.remove();

        while (entryDTO.getBeginTime().before(endDay)) {
            Entry entry = new Entry(entryDTO);
            entry.setTraining(training);
            entryService.addEntry(entry);

            entryDTO.setEndTime(addWeekToDate(entryDTO.getEndTime()));
            entryDTO.setBeginTime(addWeekToDate(entryDTO.getBeginTime()));
            duplicates.add(entryDTO);

            entryDTO = duplicates.remove();
        }
    }

    private void generateTime(Calendar calendar, Date time, Calendar beginDay) {
        calendar.clear();
        calendar.setTime(time);
        calendar.set(beginDay.get(Calendar.YEAR), beginDay.get(Calendar.MONTH),
                beginDay.get(Calendar.DAY_OF_MONTH));
    }
}
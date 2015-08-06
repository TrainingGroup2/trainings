package com.exadel.controller;

import com.exadel.dto.EntryDTO;
import com.exadel.dto.EventDTO;
import com.exadel.dto.TrainingDTO;
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
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import com.exadel.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.exadel.Utils.addWeekToDate;
import static com.exadel.Utils.getDayNumberToAdd;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('0','1','2')")
@RequestMapping(value = "/trainings", headers = "Accept=application/json")
public class TrainingsController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    TrainingEventService trainingEventService;
    @Autowired
    private EntryService entryService;
    @Autowired
    UserService userService;
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    EmailMessages emailMessages;

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
    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)   //called only by ADMIN
    public long createTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = new Training(trainingDTO);
        if (UserUtil.hasRole(0)) {
            training.setStatus(TrainingStatus.APPROVED);
        }
        else {
            training.setStatus(TrainingStatus.DRAFTED);

            trainingDTO.setId(training.getId());
            trainingDTO.setEventDescription(emailMessages.newTrainingToAdmin(training));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in Trainings", emailMessages.newTrainingToAdmin(training));
            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
            List<EventDTO> list = new ArrayList<>();
            List<Event> events = new ArrayList<>();
            events.addAll(trainingEventService.getUnwatchedEvents());
            events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
            events.addAll(userFeedbackEventService.getUnwatchedEvents());
            for (Event event: events){
                list.add(event.toEventDTO());
            }
            for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
                entry.getKey().setResult(list);

            }
        }

        long trainingId = trainingService.addTraining(training);
        if (trainingDTO.isRepeated()) {
            generateEntries(trainingDTO.getBegin(), trainingDTO.getEnd(),
                    training, trainingDTO.getEntries());
        } else {
            for (EntryDTO entryDTO : trainingDTO.getEntries()) {
                Entry entry = new Entry(entryDTO);
                entry.setTraining(training);
                entryService.addEntry(entry);
            }
        }

        return trainingId;
    }

    void generateEntries(Date beginDay, Date endDay, Training training, List<EntryDTO> entryDTOs) {

        if (beginDay == null || endDay == null)
            return;

        Calendar begin = new GregorianCalendar();
        begin.setTime(beginDay);
        int beginDayOfWeek = begin.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = new GregorianCalendar();
        for (EntryDTO entryDTO : entryDTOs) {
            calendar.clear();
            calendar.setTime(entryDTO.getBeginTime());
            calendar.set(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH),
                    begin.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DATE, getDayNumberToAdd(beginDayOfWeek, entryDTO.getDayOfWeek()));
            entryDTO.setBeginTime(calendar.getTime());

            calendar.clear();
            calendar.setTime(entryDTO.getEndTime());
            calendar.set(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH),
                    begin.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DATE, getDayNumberToAdd(beginDayOfWeek, entryDTO.getDayOfWeek()));
            entryDTO.setEndTime(calendar.getTime());
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
}
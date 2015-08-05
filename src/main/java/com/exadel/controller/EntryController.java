package com.exadel.controller;

import com.exadel.dto.AbsenteeDTO;
import com.exadel.dto.EntryDTO;
import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.model.entity.user.Absentee;
import com.exadel.model.entity.user.UserRole;
import com.exadel.service.AbsenteeService;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import com.exadel.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.exadel.Utils.addWeekToDate;

@RestController
@PreAuthorize("hasAnyRole('0','1','2')")
@RequestMapping("/training")
public class EntryController {
    @Autowired
    private EntryService entryService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private AbsenteeService absenteeService;
    @Autowired
    SmtpMailSender smtpMailSender;
    @Autowired
    EmailMessages emailMessages;
    @Autowired
    TrainingEventService trainingEventService;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/entries", method = RequestMethod.GET)
    public List<EntryDTO> getEntries(@RequestParam String trainingId,
                                     @RequestParam(required = false) Boolean future) {
        Training training = trainingService.getTrainingById(trainingId);
        List<Entry> entries;

        if (future == null || !future)
            entries = entryService.getAllEntriesByTrainingId(training.getId());
        else
            entries = entryService.findFutureEntriesByTrainingId(new Date(), training.getId());

        List<EntryDTO> entryDTOs = new ArrayList<>();

        for (Entry entry : entries) {
            entryDTOs.add(new EntryDTO(entry));
        }
        return entryDTOs;
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/nextEntry", method = RequestMethod.GET)
    public EntryDTO getNextEntry(@RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        Entry entry = entryService.findNextEntryByTrainingId(new Date(), training.getId());
        return new EntryDTO(entry);
    }

    @PreAuthorize("@trainerControlBean.isTrainer(#entryDTOs) or hasRole('0')" )
    @RequestMapping(value = "/createEntries", method = RequestMethod.POST)
    public void createEntries(@RequestParam(required = false) Long begin,
                              @RequestParam(required = false) Long end,
                              @RequestBody List<EntryDTO> entryDTOs) {
        Training training = new Training();
        training.setId(entryDTOs.get(0).getTrainingId());

        for (EntryDTO entryDTO : entryDTOs) {
            Entry entry = new Entry(entryDTO);
            entry.setTraining(training);
            entryService.addEntry(entry);
        }

        if (end != null) {
            Date endDay = new Date(end);
            Queue<EntryDTO> duplicates = new LinkedList<>(entryDTOs);
            EntryDTO entryDTO = duplicates.remove();
            entryDTO.setBeginTime(addWeekToDate(entryDTO.getBeginTime()));

            while (entryDTO.getBeginTime().before(endDay)) {
                entryDTO.setEndTime(addWeekToDate(entryDTO.getEndTime()));
                Entry entry = new Entry(entryDTO);
                entry.setTraining(training);
                entryService.addEntry(entry);
                duplicates.add(entryDTO);

                entryDTO = duplicates.remove();
                entryDTO.setBeginTime(addWeekToDate(entryDTO.getBeginTime()));
            }
        }
    }

    @RequestMapping(value = "/entry", method = RequestMethod.PUT)
    public void updateEntry(@RequestBody EntryDTO entryDTO) {
        Entry entry = entryService.getEntryById(entryDTO.getId());
        entry.updateEntry(entryDTO);
        if (UserUtil.hasRole(0)) {
            smtpMailSender.sendToUsers(trainingService.getTrainingById(entry.getTraining().getId()).getParticipants(), "Trainings", emailMessages.modifyEntry(entry));
        }
        else {
            entry.getTraining().setStatus(TrainingStatus.DRAFTED);
            trainingService.updateTraining(entry.getTraining());
            trainingEventService.addEvent(new TrainingEvent(entryDTO));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in training", entryDTO.getEventDescription());
        }
    }

    @RequestMapping(value = "/entry", method = RequestMethod.DELETE)
    public void deleteEntry(@RequestParam String entryId) {
        Entry entry = entryService.getEntryById(entryId);
        if (UserUtil.hasRole(0)) {
            entryService.deleteEntry(entry.getId());
            smtpMailSender.sendToUsers(trainingService.getTrainingById(entry.getTraining().getId()).getParticipants(), "Trainings", emailMessages.deleteEntry(entry));
        }
        else {
            entry.getTraining().setStatus(TrainingStatus.DRAFTED);
            trainingService.updateTraining(entry.getTraining());

            EntryDTO entryDTO = new EntryDTO(entry);
            entryDTO.setEventDescription(emailMessages.deleteEntryToAdmin(entry));
            trainingEventService.addEvent(new TrainingEvent(entryDTO));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in training", emailMessages.deleteEntryToAdmin(entry));
        }
    }

    @RequestMapping(value = "/absentees", method = RequestMethod.GET)
    public List<EntryDTO> getAbsentees(@RequestParam String trainingId,
                                       @RequestParam Long beginDate,
                                       @RequestParam Long endDate) {
        Training training = trainingService.getTrainingById(trainingId);
        List<Entry> entries = entryService.findEntriesForJournal(new Date(--beginDate),
                new Date(++endDate), training.getId());
        List<EntryDTO> entryDTOs = new ArrayList<>();

        for (Entry entry : entries) {
            entryDTOs.add(new EntryDTO(entry));
        }
        return entryDTOs;
    }

    @RequestMapping(value = "/absentees", method = RequestMethod.POST)
    public void createAbsentee(@RequestBody AbsenteeDTO absenteeDTO) {
        Absentee absentee = new Absentee(absenteeDTO);
        absentee.setEntry(entryService.getEntryById(absenteeDTO.getEntryId()));
        absentee.setUser(userService.getUserById(absenteeDTO.getUserId()));
        absenteeService.addAbsentee(absentee);

    }

    @RequestMapping(value = "/absentees", method = RequestMethod.PUT)
    public void updateAbsentee(@RequestBody AbsenteeDTO absenteeDTO) {
        Absentee absentee = new Absentee(absenteeDTO);
        absenteeService.updateAbsentee(absentee);
    }

    @RequestMapping(value = "/absentees", method = RequestMethod.DELETE)
    public void deleteAbsentee(@RequestParam String absenteeId) {
        absenteeService.deleteAbsentee(absenteeId);
    }
}

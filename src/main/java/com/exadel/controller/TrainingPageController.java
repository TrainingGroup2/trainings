package com.exadel.controller;

import com.exadel.model.entity.*;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingFeedbackService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainings/training")
public class TrainingPageController {

    private final TrainingFeedbackService trainingFeedbackService;
    private final EntryService entryService;

    @Autowired
    public TrainingPageController(TrainingFeedbackService trainingFeedbackService,
                                  EntryService entryService) {
        this.trainingFeedbackService = trainingFeedbackService;
        this.entryService = entryService;
    }

    @RequestMapping(value = "participants", method = RequestMethod.GET)
    public List<User> getParticipants(@RequestParam String id) {
        List<User> participants = new ArrayList<>();
        return participants;
    }

    @RequestMapping(value = "attachments", method = RequestMethod.GET)
    public List<Pair<String, String>> getAttachments(@RequestParam String id) {
        List<Pair<String, String>> attachments = new ArrayList<>();
        return attachments;
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public ExternalVisitor addExternalVisitor(@RequestBody ExternalVisitor visitor, @RequestParam String trainingId) {
        List<Training> trainings = new ArrayList<>();
        return visitor;
    }

    @RequestMapping(value = "/rating", method = RequestMethod.POST)
    public int addTrainingRating(@RequestBody int rating, @RequestParam String trainingId) {
        return rating;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Employee registerOnTraining(@RequestBody long id, @RequestParam String trainingId) {
        return new Employee();
    }

    @RequestMapping(method = RequestMethod.PUT)   //only for admin(trainer can modify, but admin must approve)
    public Training modifyTraining(@RequestBody Training training, @RequestParam String id) {

        return new Training();
    }

    @RequestMapping(value = "feedbacks", method = RequestMethod.GET)
    public List<TrainingFeedback> getFeedbacks(@RequestParam String id) {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/newFeedback", method = RequestMethod.POST)
    //@ResponseStatus(value = HttpStatus.OK)
    public void createFeedback(/*@RequestParam String id,*/ @RequestBody TrainingFeedback feedback) {
        trainingFeedbackService.addTrainingFeedback(feedback);
    }

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public Entry getEntry(@RequestParam String entryId) {
        long id = Long.parseLong(entryId);
        Entry entry = entryService.getEntryById(id).get();
        System.out.println(entry);
        return entry;
    }
}

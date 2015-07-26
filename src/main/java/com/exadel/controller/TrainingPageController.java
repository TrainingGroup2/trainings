package com.exadel.controller;

import com.exadel.model.entity.Entry;
import com.exadel.model.entity.Training;
import com.exadel.model.entity.TrainingFeedback;
import com.exadel.model.entity.User;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingFeedbackService;
import com.exadel.service.TrainingService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainings/training")
public class TrainingPageController {
    @Autowired
    private TrainingFeedbackService trainingFeedbackService;
    @Autowired
    private EntryService entryService;
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "participants", method = RequestMethod.GET)
    public List<User> getParticipants(@RequestParam String id) {
        Training training = trainingService.getTrainingById(Long.parseLong(id)).get();
        return training.getParticipants();
    }

    @RequestMapping(value = "attachments", method = RequestMethod.GET)
    public List<Pair<String, String>> getAttachments(@RequestParam String id) {
        List<Pair<String, String>> attachments = new ArrayList<>();
        return attachments;
    }

    @RequestMapping(value = "/newVisitor", method = RequestMethod.POST)
    public void addVisitor(@RequestParam String userId, @RequestParam String trainingId) {
        Training training = trainingService.getTrainingById(Long.parseLong(trainingId)).get();
        User visitor = new User();
        visitor.setId(Long.parseLong(userId));
        training.addParticipant(visitor);
        trainingService.addTraining(training);

        //TODO:  WHAT SEND TO UI?
    }

    @RequestMapping(value = "/rating", method = RequestMethod.POST)
    public int addTrainingRating(@RequestBody int rating, @RequestParam String trainingId) {
        return rating;
    }

    @RequestMapping(value = "feedbacks", method = RequestMethod.GET)
    public List<TrainingFeedback> getFeedbacks(@RequestParam String id) {
        return (List)trainingFeedbackService.getTrainingFeedbacksByTrainingId(Long.parseLong(id));
    }

    @RequestMapping(value = "/newFeedback", method = RequestMethod.POST)
    //@ResponseStatus(value = HttpStatus.OK)
    public void createFeedback(/*@RequestParam String id,*/ @RequestBody TrainingFeedback feedback) {
        /*Training training= new Training();
        training.setId(Long.parseLong(id));
        feedback.setTraining(training);*/
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

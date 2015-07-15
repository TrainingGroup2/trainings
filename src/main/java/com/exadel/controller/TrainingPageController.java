package com.exadel.controller;

import com.exadel.model.entity.Employee;
import com.exadel.model.entity.ExternalVisitor;
import com.exadel.model.entity.Training;
import com.exadel.model.entity.TrainingFeedback;
import com.exadel.model.entity.User;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainings/training")
public class TrainingPageController {

    @RequestMapping(value="participants", method = RequestMethod.GET)
    public List<User> getParticipants(@RequestParam String id) {
        List<User> participants = new ArrayList<>();
        participants.addAll(UserController.users);  //later it will be request from db for participants of this training
        return participants;
    }

    @RequestMapping(value="attachments", method = RequestMethod.GET)
    public List<Pair<String, String>> getAttachments(@RequestParam String id) {
        List<Pair<String, String>> attachments = new ArrayList<>();
        attachments.add(new Pair("lection 1", "http://..lections/lec1.ppt"));
        attachments.add(new Pair("lection 2", "http://..lections/lec2.ppt"));
        //later it will be request from db for attchmentss of this training
        return attachments;
    }

    @RequestMapping(value="/newUser", method = RequestMethod.POST)
    public ExternalVisitor addExternalVisitor(@RequestBody ExternalVisitor visitor,@RequestParam String trainingId) {
        List<Training> trainings = new ArrayList<>();
        trainings.add(TrainingsController.getTrainingById(trainingId));
        visitor.setVisitingTrainings(trainings);
        return visitor;
    }

    @RequestMapping(value="/rating", method = RequestMethod.POST)
    public int addTrainingRating(@RequestBody int rating/*,@RequestParam String trainingId*/) {
       // Training trainingToEstimate = TrainingsController.getTrainingById(trainingId);
        return rating;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Employee registerOnTraining(@RequestBody String id,@RequestParam String trainingId) {
        Employee employee = new Employee(UserController.getUserById(id));
        List<Training> trainings = new ArrayList<>();
        trainings.add(TrainingsController.getTrainingById(trainingId));
        employee.setVisitingTrainings(trainings);
        return employee;

    }

    @RequestMapping(method = RequestMethod.PUT)   //only for admin(trainer can modify, but admin must approve)
    public Training modifyTraining(@RequestBody Training training,@RequestParam String id) {
        Training trainingToUpdate = TrainingsController.getTrainingById(id);
        if (trainingToUpdate != null) {
            trainingToUpdate.updateTraining(training);
        }
        return training;
    }

    @RequestMapping(value="feedbacks", method = RequestMethod.GET)
    public List<TrainingFeedback> getFeedbacks(@RequestParam String id) {
        List<TrainingFeedback> feedbacks = new ArrayList<>();
        feedbacks.add(new TrainingFeedback("1", new Training(), true, true, true, 4, true, true, "very good", UserPageController.employee, new Date()));
        feedbacks.add(new TrainingFeedback("2", new Training(), false, false, false, 4, false, false, "very bad", UserPageController.employee, new Date()));
        //later it will be request from db for attchmentss of this training
        return feedbacks;
    }

    @RequestMapping(value="/newFeedback", method = RequestMethod.POST)
    public TrainingFeedback createFeedback(@RequestParam String id, @RequestBody TrainingFeedback feedback) {
        //add to db feedback
        return feedback;
    }
}

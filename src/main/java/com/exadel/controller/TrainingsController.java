package com.exadel.controller;

import com.exadel.model.entity.Training;
import com.exadel.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingsController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Training> getTrainings() {
        List<Training> trainings = (List<Training>)trainingService.getAllTrainings();
        return trainings;
    }

    @RequestMapping(value="/training", method = RequestMethod.GET)
    public Training getTraining(@RequestParam String id) {
        return trainingService.getTrainingById(Long.parseLong(id)).get();
    }

    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)    //DOESN'T WORK
    public Training createTraining(@RequestBody Training training) {
        System.out.println(training);
        System.out.println(training.getId());
        System.out.println(training.getDescription());
        System.out.println(training.getName());
        System.out.println(training.getTrainer());
        System.out.println(training.getParticipants());
        trainingService.addTraining(training);
        return new Training();
    }

    @RequestMapping(value = "/training", method = RequestMethod.PUT)   //DOESN'T WORK
    public Training modifyTraining(@RequestBody Training training) {
        trainingService.addTraining(training);
        return training;
    }

    @RequestMapping(value="/training", method = RequestMethod.DELETE)
    public void deleteTraining(@RequestParam String id) {
        trainingService.deleteById(Long.parseLong(id));
        // or get training and set status CANCELLED
    }
}
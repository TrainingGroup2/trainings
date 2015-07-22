package com.exadel.controller;

import com.exadel.model.entity.Training;
import com.exadel.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingsController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingsController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Training> getTrainings() {
        List<Training> trainings = (List<Training>)trainingService.getAllTrainings();
        System.out.println(trainings);
        return trainings;
    }

    @RequestMapping(value="/training", method = RequestMethod.GET)
    public Training getTraining(@RequestParam String id) {
        return trainingService.getTrainingById(Long.parseLong(id)).get();
    }

    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)    //DOESN'T WORK
    public Training createTraining(@RequestBody Training training) {
        trainingService.addTraining(training);
        return training;
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
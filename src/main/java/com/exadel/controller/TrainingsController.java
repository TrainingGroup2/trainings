package com.exadel.controller;

import com.exadel.model.constants.TrainingStatus;
import com.exadel.model.entity.Employee;
import com.exadel.model.entity.Entry;
import com.exadel.model.entity.Training;
import com.exadel.model.entity.User;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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

    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)
    public Training createTraining(@RequestBody Training training) {
        trainingService.addTraining(training);
        return training;
    }

    /*@RequestMapping(value="/training", method = RequestMethod.DELETE)
    public void deleteTraining(@RequestParam String id) {
        Training trainingToDelete = TrainingsController.getTrainingById(id);
        if (trainingToDelete != null) {
            trainings.remove(trainingToDelete);
        }
    }*/
}
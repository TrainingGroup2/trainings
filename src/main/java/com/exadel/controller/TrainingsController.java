package com.exadel.controller;

import com.exadel.dto.TrainingDTO;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.exception.UserNotFoundException;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/trainings", headers = "Accept=application/json")
public class TrainingsController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingDTO> getTrainings() {
        List<Training> trainings = (List<Training>) trainingService.getAllTrainings();
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }

    @RequestMapping(value = "/training", method = RequestMethod.GET)
    public TrainingDTO getTraining(@RequestParam String id) {
        return new TrainingDTO(trainingService.getTrainingById(id));
    }

    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)
    public void createTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = new Training(trainingDTO);
        ExternalTrainer trainer = userService.getTrainerById(String.valueOf(trainingDTO.getTrainerId()));
        training.setTrainer(trainer);
        trainingService.addTraining(training);
    }
}
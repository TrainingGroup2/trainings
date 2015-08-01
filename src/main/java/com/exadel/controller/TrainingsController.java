package com.exadel.controller;

import com.exadel.dto.TrainingDTO;
import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('0','1','2')")
@RequestMapping(value = "/trainings", headers = "Accept=application/json")

public class TrainingsController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private UserService userService;
    @Autowired
    TrainingEventService trainingEventService;

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
    public void createTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = new Training(trainingDTO);
        if (UserServiceImpl.hasRole(0)) {
            training.setStatus(TrainingStatus.APPROVED);
            trainingService.addTraining(training);
        }
        else {
            training.setStatus(TrainingStatus.DRAFTED);
            trainingService.addTraining(training);

            trainingDTO.setId(training.getId());
            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
        }
    }
}
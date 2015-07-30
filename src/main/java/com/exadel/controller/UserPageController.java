package com.exadel.controller;

import com.exadel.dto.TrainingDTO;
import com.exadel.dto.UserDTO;
import com.exadel.dto.UserFeedbackDTO;
import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;
import com.exadel.service.UserFeedbackService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserPageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserFeedbackService userFeedbackService;

    @RequestMapping(method = RequestMethod.GET)
    public UserDTO showUser(@RequestParam String id) {
        User user = userService.getUserById(id);
        return new UserDTO(user);
    }

    @RequestMapping(value = "/mentoringTrainings", method = RequestMethod.GET)
    public List<TrainingDTO> showMentoringTrainings(@RequestParam String userId) {
        return getTrainingDTOs(userService.getMentoringTrainings(userId));
    }

    @RequestMapping(value = "/visitingTrainings", method = RequestMethod.GET)
    public List<TrainingDTO> showVisitingTrainings(@RequestParam String userId) {
        return getTrainingDTOs(userService.getVisitingTrainings(userId));
    }

    List<TrainingDTO> getTrainingDTOs(List<Training> trainings) {
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }

    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    public List<UserFeedbackDTO> getFeedbacks(@RequestParam String userId) {
        User user = userService.getUserById(userId);
        List<UserFeedback> feedbacks = userFeedbackService.getFeedbacksByVisitor(user.getId());

        List<UserFeedbackDTO> feedbackDTOs = new ArrayList<>();
        for (UserFeedback feedback : feedbacks) {
            feedbackDTOs.add(new UserFeedbackDTO(feedback));
        }
        return feedbackDTOs;
    }

    @RequestMapping(value = "/newFeedback", method = RequestMethod.POST)
    public void createFeedback(@RequestBody UserFeedbackDTO feedbackDTO) {
        UserFeedback feedback = new UserFeedback(feedbackDTO);
        feedback.setTrainer(userService.getTrainerById(String.valueOf(feedbackDTO.getTrainerId())));
        feedback.setVisitor(userService.getEmployeeById(String.valueOf(feedbackDTO.getVisitorId())));
        userFeedbackService.addFeedback(feedback);
    }
}

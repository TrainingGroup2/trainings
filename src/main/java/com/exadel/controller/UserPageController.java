package com.exadel.controller;

import com.exadel.dto.TrainingDTO;
import com.exadel.dto.UserDTO;
import com.exadel.dto.UserFeedbackDTO;
import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;
import com.exadel.service.UserFeedbackService;
import com.exadel.service.UserService;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;

    @PreAuthorize("hasRole('0') or  @userControlBean.isMyAccount(#id) and hasAnyRole('1','2')")
    @RequestMapping(method = RequestMethod.GET)
    public UserDTO showUser(@RequestParam String id) {
        User user = userService.getUserById(id);
        return new UserDTO(user);
    }
    @PreAuthorize("hasRole('0') or @userControlBean.isMyAccount(#userId) and hasAnyRole('1','2')")
    @RequestMapping(value = "/mentoringTrainings", method = RequestMethod.GET)
    public List<TrainingDTO> showMentoringTrainings(@RequestParam String userId) {
        return getTrainingDTOs(userService.getMentoringTrainings(userId));
    }
    @PreAuthorize("hasRole('0') or  @userControlBean.isMyAccount(#userId) and hasRole('1')")
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
    @PreAuthorize("hasRole('0') or @userControlBean.isMyAccount(#userId) and hasAnyRole('1','2')")
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
    @PreAuthorize("hasAnyRole('1','2')")
    @RequestMapping(value = "/newFeedback", method = RequestMethod.POST)
    public void createFeedback(@RequestBody UserFeedbackDTO feedbackDTO) {
        UserFeedback feedback = new UserFeedback(feedbackDTO);
        userFeedbackService.addFeedback(feedback);

        feedbackDTO.setId(feedback.getId());
        userFeedbackEventService.addEvent(new UserFeedbackEvent(feedbackDTO));
    }

    @RequestMapping(value="/info",method = RequestMethod.GET)
    public UserDTO showUserInfo(@RequestParam String name) {
        User user = userService.getUserByLogin(name);
        return new UserDTO(user);
    }
}

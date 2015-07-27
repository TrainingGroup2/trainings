package com.exadel.controller;

import com.exadel.model.constants.UserRole;
import com.exadel.model.entity.*;
import com.exadel.service.UserFeedbackService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserPageController {
    private final UserService userService;
    private final UserFeedbackService userFeedbackService;

    @Autowired
    public UserPageController(UserService userService, UserFeedbackService userFeedbackService) {
        this.userService = userService;
        this.userFeedbackService = userFeedbackService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public User showUser(@RequestParam String id) {
        Optional<User> user = userService.getUserById(Long.parseLong(id));
        return user.get();
    }

    @RequestMapping(value = "/mentoringTrainings", method = RequestMethod.GET)
    public List<Training> showMentoringTrainings(@RequestParam String userId) {
        User user = userService.getUserById(Long.parseLong(userId)).get();
        if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.EMPLOYEE || user.getRole() == UserRole.EXTERNAL_TRAINER)
            return ((ExternalTrainer)user).getMentoringTrainings();

        return null; // TODO: EXCEPTION HANDLING!!!
    }

    @RequestMapping(value = "/visitingTrainings", method = RequestMethod.GET)
    public List<Training> showVisitingTrainings(@RequestParam String userId) {
        User user = userService.getUserById(Long.parseLong(userId)).get();
        if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.EMPLOYEE)
            return ((Employee)user).getVisitingTrainings();
        else if (user.getRole() == UserRole.EXTERNAL_VISITOR)
            return ((ExternalVisitor)user).getVisitingTrainings();

        return null; // TODO: EXCEPTION HANDLING!!!
    }

    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    public List<UserFeedback> getFeedbacks(@RequestParam String userId) {
        return (List)userFeedbackService.getFeedbacksByVisitor(Long.parseLong(userId));
    }

    @RequestMapping(value = "/newFeedback", method = RequestMethod.POST)
    public UserFeedback createFeedback(@RequestBody UserFeedback feedback) {
        return userFeedbackService.addFeedback(feedback);
    }
}

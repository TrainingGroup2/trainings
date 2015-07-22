package com.exadel.controller;

import com.exadel.model.entity.ExternalTrainer;
import com.exadel.model.entity.ExternalVisitor;
import com.exadel.model.entity.User;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TrainingService trainingService;

    @Autowired
    public UserController(UserService userService, TrainingService trainingService) {
        this.userService = userService;
        this.trainingService = trainingService;
    }

    @RequestMapping(value = "/newTrainer", method = RequestMethod.POST)
    public ExternalTrainer addExternalTrainer(@RequestBody ExternalTrainer trainer) {
        userService.addUser(trainer);
        return trainer;
    }

    @RequestMapping(value = "/newVisitor", method = RequestMethod.POST)
    public ExternalVisitor addExternalVisitor(@RequestBody ExternalVisitor visitor) {
        userService.addUser(visitor);
        return visitor;
    }

    @RequestMapping(value = "/editTrainer", method = RequestMethod.PUT)
    public ExternalTrainer EditExternalTrainer(@RequestBody ExternalTrainer trainer) {
        userService.addUser(trainer);
        return trainer;
    }

    @RequestMapping(value = "/editVisitor", method = RequestMethod.PUT)
    public ExternalVisitor editExternalVisitor(@RequestBody ExternalVisitor visitor) {
        userService.addUser(visitor);
        return visitor;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> showUsers() {
        List<User> users = (List<User>)userService.getAllUsers();
        return users;
    }
}
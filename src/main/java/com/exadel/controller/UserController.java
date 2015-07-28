package com.exadel.controller;

import com.exadel.dto.UserDTO;
<<<<<<< HEAD
=======
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.model.entity.user.ExternalVisitor;
>>>>>>> Add DTO to Controllers
import com.exadel.model.entity.user.User;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/newTrainer", method = RequestMethod.POST)
    public void addExternalTrainer(@RequestBody UserDTO trainerDTO) {
        userService.addUser(new User(trainerDTO));
    }

    @RequestMapping(value = "/newVisitor", method = RequestMethod.POST)
    public void addExternalVisitor(@RequestBody UserDTO visitorDTO) {
        userService.addUser(new User(visitorDTO));
    }

    @RequestMapping(value = "/editTrainer", method = RequestMethod.PUT)
    public void editExternalTrainer(@RequestBody UserDTO trainerDTO) {
        userService.updateUser(new User(trainerDTO));
    }

    @RequestMapping(value = "/editVisitor", method = RequestMethod.PUT)
    public void editExternalVisitor(@RequestBody UserDTO visitorDTO) {
        userService.updateUser(new User(visitorDTO));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> showUsers() {
        List<User> users = (List<User>)userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }
}
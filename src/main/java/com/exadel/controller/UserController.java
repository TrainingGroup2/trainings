package com.exadel.controller;

import com.exadel.dto.UserDTO;
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.model.entity.user.User;
import com.exadel.service.UserService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasRole('0')")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    private EmailMessages emailMessages;

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/newTrainer", method = RequestMethod.POST)
    public void addExternalTrainer(@RequestBody UserDTO trainerDTO) {
        ExternalTrainer trainer = (ExternalTrainer)userService.addUser(new ExternalTrainer(trainerDTO));

        String password = UUID.randomUUID().toString().substring(0, 16);
        smtpMailSender.send(trainer.getEmail(), "Exadel-Trainings System", emailMessages.newExternalTrainer(trainer, password));
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/newVisitor", method = RequestMethod.POST)
    public void addExternalVisitor(@RequestBody UserDTO visitorDTO) {
        userService.addUser(new User(visitorDTO));
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/editTrainer", method = RequestMethod.PUT)
    public void editExternalTrainer(@RequestBody UserDTO trainerDTO) {
        User user = new User(trainerDTO);
        userService.updateUser(user);
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/editVisitor", method = RequestMethod.PUT)
    public void editExternalVisitor(@RequestBody UserDTO visitorDTO) {
        User user = new User(visitorDTO);
        userService.updateUser(user);
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> showUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public List<UserDTO> showTrainers() {
        List<User> users = userService.getTrainers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    @RequestMapping(value = "/pages/count/{pageNumber}", method = RequestMethod.GET)
    public Integer getCount(@PathVariable Integer pageNumber, @RequestParam Integer size) {
        Page<User> page = userService.getUsers(pageNumber, size);
       Integer count = page.getTotalPages();
        return count;
    }


    @RequestMapping(value ="/{pageNumber}",method = RequestMethod.GET)
    public List<UserDTO> showUsers(@PathVariable Integer pageNumber, @RequestParam Integer size) {
        Page<User> page = userService.getUsers(pageNumber, size);
        List<User> users =  page.getContent();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

}
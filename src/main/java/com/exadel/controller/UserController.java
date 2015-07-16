package com.exadel.controller;

/**
 * Created by Виктория on 10.07.2015.
 */

import com.exadel.model.entity.Employee;
import com.exadel.model.entity.ExternalTrainer;
import com.exadel.model.entity.Training;
import com.exadel.model.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

   static List<User> users = new ArrayList<>();

        static {
            users.add(new Employee("1234455", "Petr", "Sidorov", "+3752968688998","petya@mail.ru"));
            users.add(new Employee("12355", "Aleksey", "Petrov","+3752966899988", "lesha@mail.ru"));
            users.add(new Employee("1255", "Marina", "Ivanova","+375295655898", "marina@mail.ru"));
        }

    public static User getUserById(String id)
    {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @RequestMapping(value="/newTrainer", method = RequestMethod.POST)
    public ExternalTrainer addExternalTrainer(@RequestBody ExternalTrainer trainer,@RequestParam String trainingId) {
        List<Training> trainings = new ArrayList<>();
        trainings.add(TrainingsController.getTrainingById(trainingId));
        trainer.setMentoringTrainings(trainings);
        return trainer;
    }

    @RequestMapping(method = RequestMethod.GET)
    public  List<User> showUsers() {
        return users;
    }
}
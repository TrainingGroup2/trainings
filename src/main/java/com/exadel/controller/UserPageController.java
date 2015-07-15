package com.exadel.controller;

import com.exadel.model.entity.Employee;
import com.exadel.model.entity.Training;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Виктория on 10.07.2015.
 */

@RestController
@RequestMapping("/user")
public class UserPageController {
    static Employee employee;
    static Employee defaultEmployee;
    private static Logger logger = Logger.getLogger(UserPageController.class.getName());


    static {
        List<Training> list1 = new ArrayList<>();
        List<Training> list2 = new ArrayList<>();
        Employee trainer = new Employee("1255", "Marina", "Ivanova", "Aleksandrovna", "+37529656599", "marina@mail.ru", list1, list1);
        list1.add(new Training("1", "Training1", "204", new Date(), new Date(), trainer, "developers", 4.5, 32, 2, 8));
        list1.add(new Training("2", "Training2", "204", new Date(), new Date(), trainer, "testers", 4.5, 2, 7, 9));
        list2.add(new Training("3", "Training13", "205", new Date(), new Date(), trainer, "developers", 2.7, 27, 4, 6));
        employee = new Employee("2", "Marina", "Ivanova", "Aleksandrovna", "+37529656599", "marina@mail.ru", list1, list2);
        defaultEmployee = new Employee("5", "Vasiliy", "Pupkin", "Sergeevich", "+3752957659989", "vaspup@mail.ru", list1, list2);
    }

    Employee getUserById(String id) {
        if (id.equals(employee.getId())) {
            return employee;
        } else
            return defaultEmployee;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Employee showUser(@RequestParam String id) {
        Employee employee = getUserById(id);
        return employee;
    }

    @RequestMapping(value = "/mentoringTrainings", method = RequestMethod.GET)
    public List<Training> showMentoringTrainings(@RequestParam String userId) {
        Employee employee = getUserById(userId);
        return employee.getMentoringTrainings();
    }

    @RequestMapping(value = "/visitingTrainings", method = RequestMethod.GET)
    public List<Training> showVisitingTrainings(@RequestParam String userId) {
        Employee employee = getUserById(userId);
        return employee.getVisitingTrainings();
    }


}


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
    static Employee emp;
    static Employee defaultEmp;
    private static Logger logger = Logger.getLogger(UserPageController.class.getName());


    static {
        List<Training> list1 = new ArrayList<>();
        List<Training> list2 = new ArrayList<>();
        list1.add(new Training("1", "Marina", "204", new Date(), new Date(), "1", "1st best trainer", "developers", 4.5, 32, 2, 8));
        list1.add(new Training("5", "Marina", "204", new Date(), new Date(), "2", "5 best trainer", "testers", 4.5, 2, 7, 9));
        list2.add(new Training("1", "Petya", "205", new Date(), new Date(), "3", "2nd best trainer", "developers", 2.7, 27, 4, 6));
        emp = new Employee("1255", "Marina", "Ivanova", "Aleksandrovna", "+37529656599", "marina@mail.ru", list1, list2);
        defaultEmp = new Employee("1", "Vasiliy", "Pupkin", "Sergeevich", "+3752957659989", "vaspup@mail.ru", list1, list2);
    }

    Employee getUserById(String id) {
        if (id.equals(emp.getId())) {
            return emp;
        } else
            return defaultEmp;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Employee showUser(@RequestParam String id) {
        Employee up = getUserById(id);
        return up;
    }

    @RequestMapping(value = "/mentoringTrainings", method = RequestMethod.GET)
    public List<Training> showMentoringTrainings(@RequestParam String userId) {
        Employee up = getUserById(userId);
        return up.getMentoringTrainings();
    }

    @RequestMapping(value = "/visitingTrainings", method = RequestMethod.GET)
    public List<Training> showVisitingTrainings(@RequestParam String userId) {
        Employee up = getUserById(userId);
        return up.getVisitingTrainings();
    }


}


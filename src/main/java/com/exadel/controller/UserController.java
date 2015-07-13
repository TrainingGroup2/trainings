package com.exadel.controller;

/**
 * Created by Виктория on 10.07.2015.
 */

import java.util.ArrayList;
import java.util.List;

import com.exadel.model.entity.Employee;
import com.exadel.model.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

   static List<User> users = new ArrayList<>();

        static {
            users.add(new Employee("1234455", "Petr", "Ivanovich", "Sidorov", "+3752968688998","petya@mail.ru"));
            users.add(new Employee("12355", "Aleksey", "Ivanovich", "Petrov","+3752966899988", "lesha@mail.ru"));
            users.add(new Employee("1255", "Marina", "Aleksandrovna", "Ivanova","+375295655898", "marina@mail.ru"));
        }



    @RequestMapping(method = RequestMethod.GET)
    public  List<User> showUsers() {
        return users;
    }
}
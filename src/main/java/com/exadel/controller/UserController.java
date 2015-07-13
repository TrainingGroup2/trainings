package com.exadel.controller;

/**
 * Created by Виктория on 10.07.2015.
 */

import java.util.ArrayList;
import java.util.List;

import com.exadel.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/users")
public class UserController {

   static List<User> users = new ArrayList<>();

        static {
            users.add(new User("1234455", "Petr", "Ivanovich", "Sidorov", "petya@mail.ru"));
            users.add(new User("12355", "Aleksey", "Ivanovich", "Petrov", "lesha@mail.ru"));
            users.add(new User("1255", "Marina", "Aleksandrovna", "Ivanova", "marina@mail.ru"));
        }



    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<User> showUsers() {
        return users;
    }
}
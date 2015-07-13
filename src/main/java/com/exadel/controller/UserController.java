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

    List<User> users = new ArrayList<>();
    public void setUsers() {

       User a = new User("1234455", "Petr", "Ivanovich", "Sidorov", "petya@mail.ru");
       User b = new User("12355", "Aleksey", "Ivanovich", "Petrov", "lesha@mail.ru");
       User c = new User("1255", "Marina", "Aleksandrovna", "Ivanova", "marina@mail.ru");
       users.add(a);
       users.add(b);
       users.add(c);
   }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<User> showUsers() {
        setUsers();
        return users;
    }
}
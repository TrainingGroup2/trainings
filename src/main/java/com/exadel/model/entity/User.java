package com.exadel.model.entity;

/**
 * Created by Виктория on 10.07.2015.
 */
public class User {
    private  String id;
    private  String name;
    private  String secondName;
    private  String surname;
    private  String email;
    // private  List<Training> myTrainingList; ;


    public User(String id, String name, String secondName, String surname, String email) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.surname = surname;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}

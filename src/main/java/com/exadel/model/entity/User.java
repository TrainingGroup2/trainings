package com.exadel.model.entity;

/**
 * Created by Виктория on 10.07.2015.
 */
public class User {
    private  long id;
    private  String name;
    private  String secondname;
    private  String surname;
    private  String email;
    // private  List<Training> myTrainingList; ;

    public User(long id, String name, String secondname, String surname, String email) {
        this.id = id;
        this.name = name;
        this.secondname = secondname;
        this.surname = surname;
        this.email = email;
    }

    public User(String id, String name, String secondname, String surname, String email) {
        this.id = Long.parseLong(id);
        this.name = name;
        this.secondname = secondname;
        this.surname = surname;
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
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

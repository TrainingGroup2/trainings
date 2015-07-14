package com.exadel.model.entity;

/**
 * Created by Виктория on 13.07.2015.
 */
public abstract class User {
    private String id;
    private String name;
    private String secondName;
    private String surname;
    private String phone;
    private String email;

    public User(){}

    public User(String id, String name, String secondName, String surname, String phone, String email) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.surname = surname;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

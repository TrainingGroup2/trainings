package com.exadel.model.entity;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Виктория on 13.07.2015.
 */

public class User {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private String email;

    public User(){}

    public User(String id, String name, String surname, String phone, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String generateId() {
        Random random = new Random();
        return new BigInteger(130,random).toString(32);
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

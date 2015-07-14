package com.exadel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 10.07.2015.
 */
public class Employee extends User {
    @JsonIgnore
    private List<Training> visitingTrainings;
    @JsonIgnore
    private List<Training> mentoringTrainings;

    public Employee() {
        super();
    }

    public Employee(String id, String name, String secondName, String surname, String phone, String email,List<Training> visitingTrainings,    List<Training> mentoringTrainings) {
        super(id, name, secondName, surname, phone, email);
        this.visitingTrainings = visitingTrainings;
        this.mentoringTrainings = mentoringTrainings;
    }

    public Employee(String id, String name, String secondName, String surname, String phone, String email) {
        super(id, name, secondName, surname, phone, email);
        this.mentoringTrainings = new ArrayList<>();
        this.visitingTrainings = new ArrayList<>();
    }

    public List<Training> getVisitingTrainings() {
        return visitingTrainings;
    }

    public void setVisitingTrainings(List<Training> visitingTrainings) {
        this.visitingTrainings = visitingTrainings;
    }

    public List<Training> getMentoringTrainings() {
        return mentoringTrainings;
    }

    public void setMentoringTrainings(List<Training> mentoringTrainings) {
        this.mentoringTrainings = mentoringTrainings;
    }
}

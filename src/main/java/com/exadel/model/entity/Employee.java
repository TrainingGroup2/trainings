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
    private List<TrainingFeedback> writedFeedbacks;
    private List<UserFeedback> receivedFeedbacks;

    public Employee() {
        super();
    }

    public Employee(String id, String name, String surname, String phone, String email,List<Training> visitingTrainings,    List<Training> mentoringTrainings) {
        super(id, name, surname, phone, email);
        this.visitingTrainings = visitingTrainings;
        this.mentoringTrainings = mentoringTrainings;
    }

    public Employee(String id, String name, String surname, String phone, String email) {
        super(id, name, surname, phone, email);
        this.mentoringTrainings = new ArrayList<>();
        this.visitingTrainings = new ArrayList<>();
    }
    public Employee(User user) {
        super(user.generateId(), user.getName(), user.getSurname(),user.getPhone(),  user.getEmail());
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

    public List<TrainingFeedback> getWritedFeedbacks() {
        return writedFeedbacks;
    }

    public void setWritedFeedbacks(List<TrainingFeedback> writedFeedbacks) {
        this.writedFeedbacks = writedFeedbacks;
    }

    public List<UserFeedback> getReceivedFeedbacks() {
        return receivedFeedbacks;
    }

    public void setReceivedFeedbacks(List<UserFeedback> receivedFeedbacks) {
        this.receivedFeedbacks = receivedFeedbacks;
    }
}

package com.exadel.dto;

import com.exadel.model.entity.feedback.EnglishLevel;
import com.exadel.model.entity.feedback.UserFeedback;

import java.util.Date;

public class UserFeedbackDTO {
    private long id;
    private long visitorId; //we can leave feedback only on employee
    private long trainerId; //who leaves feedback
    private String attendance;
    private String attitude;
    private String communicationSkills;
    private boolean questions;
    private boolean interest;
    private boolean focusOnResult;
    private EnglishLevel level;
    private int grade; //for English from 1 to 4
    private String otherInfo;
    private Date date;

    private String eventDescription;

    public UserFeedbackDTO() {}

    public UserFeedbackDTO(UserFeedback feedback) {
        this.id = feedback.getId();
        this.visitorId = feedback.getVisitor().getId();
        this.trainerId = feedback.getTrainer().getId();
        this.attendance = feedback.getAttendance();
        this.attitude = feedback.getAttitude();
        this.communicationSkills = feedback.getCommunicationSkills();
        this.questions = feedback.isQuestions();
        this.interest = feedback.isInterest();
        this.focusOnResult = feedback.isFocusOnResult();
        this.level = feedback.getLevel();
        this.grade = feedback.getGrade();
        this.otherInfo = feedback.getOtherInfo();
        this.date = feedback.getDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getCommunicationSkills() {
        return communicationSkills;
    }

    public void setCommunicationSkills(String communicationSkills) {
        this.communicationSkills = communicationSkills;
    }

    public boolean isQuestions() {
        return questions;
    }

    public void setQuestions(boolean questions) {
        this.questions = questions;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public boolean isFocusOnResult() {
        return focusOnResult;
    }

    public void setFocusOnResult(boolean focusOnResult) {
        this.focusOnResult = focusOnResult;
    }

    public EnglishLevel getLevel() {
        return level;
    }

    public void setLevel(EnglishLevel level) {
        this.level = level;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}

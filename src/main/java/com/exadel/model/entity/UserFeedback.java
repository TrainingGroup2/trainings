package com.exadel.model.entity;

import com.exadel.model.constants.EnglishLevel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

public class UserFeedback {
    private String id;
    private Employee visitor;  //we can leave feedback only on employee
    private String attendance;
    private String attitude;
    private String communicationSkills;
    private boolean questions;
    private boolean interest;
    private boolean focusOnResult;
    private EnglishLevel englishLevel;
    @Min(1)
    @Max(4)
    private int grade;    //for English from 1 to 4
    private String otherInfo;
    private User feedbacker;   //who leaves feedback
    private Date date;

    public UserFeedback() {}

    public UserFeedback(String id, Employee visitor, String attendance, String attitude, String communicationSkills, boolean questions, boolean interest,
                        boolean focusOnResult, EnglishLevel englishLevel, int grade, String otherInfo, User feedbacker, Date date) {
        this.id = id;
        this.visitor = visitor;
        this.attendance = attendance;
        this.attitude = attitude;
        this.communicationSkills = communicationSkills;
        this.questions = questions;
        this.interest = interest;
        this.focusOnResult = focusOnResult;
        this.englishLevel = englishLevel;
        this.grade = grade;
        this.otherInfo = otherInfo;
        this.feedbacker = feedbacker;
        this.date = date;
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

    public User getFeedbacker() {
        return feedbacker;
    }

    public void setFeedbacker(User feedbacker) {
        this.feedbacker = feedbacker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getVisitor() {
        return visitor;
    }

    public void setVisitor(Employee visitor) {
        this.visitor = visitor;
    }

    public EnglishLevel getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(EnglishLevel englishLevel) {
        this.englishLevel = englishLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.exadel.model.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

public class UserFeedback {
    private String attendance;
    private String attitude;
    private String communicationSkills;
    private boolean questions;
    private boolean interest;
    private boolean focusOnResult;
    private String level;   //for English PreInt, Int, UppInt, Ad
    @Min(1)
    @Max(4)
    private int rating;    //for English from 1 to 4
    private String otherInfo;
    private User feedbacker;
    private Date date;

    public UserFeedback() {}

    public UserFeedback(String attendance, String attitude, String communicationSkills, boolean questions, boolean interest,
                        boolean focusOnResult, String level, int rating, String otherInfo, User feedbacker, Date date) {
        this.attendance = attendance;
        this.attitude = attitude;
        this.communicationSkills = communicationSkills;
        this.questions = questions;
        this.interest = interest;
        this.focusOnResult = focusOnResult;
        this.level = level;
        this.rating = rating;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}

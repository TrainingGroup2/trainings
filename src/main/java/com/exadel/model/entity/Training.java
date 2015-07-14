package com.exadel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Training {
    private String id;
    private String name;
    private String place;
    private Date beginTime;
    private Date endTime;
    private String trainerId;  //to define who you are on the welcome page
    private String trainer;
    private String targetAudience;
    private double rating;
    private int feedbackNumber;
    private int membersCountMax;
    private int membersCount;

    public Training() {
    }

    public Training(String id, String name, String place, Date beginTime, Date endTime, String trainerId, String trainer, String targetAudience, double rating, int feedbackNumber, int membersCountMax, int membersCount) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.trainerId = trainerId;
        this.trainer = trainer;
        this.targetAudience = targetAudience;
        this.rating = rating;
        this.feedbackNumber = feedbackNumber;
        this.membersCountMax = membersCountMax;
        this.membersCount = membersCount;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getFeedbackNumber() {
        return feedbackNumber;
    }

    public void setFeedbackNumber(Integer feedbackNumber) {
        this.feedbackNumber = feedbackNumber;
    }

    public int getMembersCountMax() {
        return membersCountMax;
    }

    public void setMembersCountMax(Integer membersCountMax) {
        this.membersCountMax = membersCountMax;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setFeedbackNumber(int feedbackNumber) {
        this.feedbackNumber = feedbackNumber;
    }

    public void setMembersCountMax(int membersCountMax) {
        this.membersCountMax = membersCountMax;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }
}

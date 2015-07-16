package com.exadel.model.entity;

import com.exadel.model.constants.TrainingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Training {
    private String id;
    private String name;
    private String place;
    private Date beginTime;
    private Date endTime;
    private User trainer;
    private String targetAudience;
    private String language;
    private double rating;
    private int membersCountMax;
    private int membersCount;
    private TrainingStatus status;
    private List<User> participants;
    private List<TrainingFeedback> feedbacks;

    public Training() {
    }

    public Training(String id, String name, String place, Date beginTime, Date endTime, User trainer, String targetAudience, String language,
                    double rating, int membersCountMax, int membersCount, TrainingStatus status, List<User> participants, List<TrainingFeedback> feedbacks) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.trainer = trainer;
        this.targetAudience = targetAudience;
        this.language = language;
        this.rating = rating;
        this.membersCountMax = membersCountMax;
        this.membersCount = membersCount;
        this.status = status;
        this.participants = participants;
        this.feedbacks = feedbacks;
    }

    public String getId() {
        return id;
    }

    public void updateTraining(Training training)
    {
        this.setId(training.getId());
        this.setName(training.getName());
        this.setPlace(training.getPlace());
        this.setBeginTime(training.getBeginTime());
        this.setEndTime(training.getEndTime());
        this.setTrainer(training.getTrainer());
        this.setTargetAudience(training.getTargetAudience());
        this.setLanguage((training.getLanguage()));
        this.setRating(training.getRating());
        this.setMembersCount(training.getMembersCount());
        this.setMembersCountMax(training.getMembersCountMax());
        this.setStatus(training.getStatus());
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

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMembersCountMax() {
        return membersCountMax;
    }

    public void setMembersCountMax(int membersCountMax) {
        this.membersCountMax = membersCountMax;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<TrainingFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<TrainingFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

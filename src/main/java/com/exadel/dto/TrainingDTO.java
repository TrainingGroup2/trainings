package com.exadel.dto;

import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;

public class TrainingDTO {
    private long id;
    private String name;
    private String targetAudience;
    private String language;
    private boolean isExternal;
    private String description;
    private TrainingStatus status;
    private int membersCountMax;
    private int membersCount;
    private double rating;
    private long trainerId;

    private String eventDescription;

    public TrainingDTO() {
    }

    public TrainingDTO(Training training) {
        this.id = training.getId();
        this.name = training.getName();
        this.targetAudience = training.getTargetAudience();
        this.language = training.getLanguage();
        this.isExternal = training.IsExternal();
        this.description = training.getDescription();
        this.status = training.getStatus();
        this.membersCountMax = training.getMembersCountMax();
        this.membersCount = training.getMembersCount();
        this.rating = training.getRating();
        this.trainerId = training.getTrainer().getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public void setIsExternal(boolean isExternal) {
        this.isExternal = isExternal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
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

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}

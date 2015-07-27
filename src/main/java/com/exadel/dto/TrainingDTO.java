package com.exadel.dto;

import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingDTO {
    private long id;
    private String name;
    private String targetAudience;
    private String language;
    private boolean isExternal;
    private String description;
    private TrainingStatus status;
    private int membersCountMax;
    private double rating;
    private UserDTO trainer;
    private boolean repeated;
    private List<EntryDTO> entries;

    private Date begin;
    private Date end;

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
        this.rating = training.getRating();
        this.trainer = new UserDTO(training.getTrainer());
        this.repeated = training.isRepeated();

        this.entries = null;
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

    public UserDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(UserDTO trainer) {
        this.trainer = trainer;
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

    public List<EntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryDTO> entries) {
        this.entries = entries;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }
}
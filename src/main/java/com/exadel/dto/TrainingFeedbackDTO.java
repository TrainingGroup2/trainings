package com.exadel.dto;

import com.exadel.model.entity.feedback.TrainingFeedback;

import java.util.Date;

public class TrainingFeedbackDTO {
    private long id;
    private long trainingId;
    private boolean understandable;
    private boolean interesting;
    private boolean newKnowledge;
    private int effectiveness;
    private boolean studyWithTrainer;
    private boolean recommend;
    private String otherInfo;
    private long feedbackerId; // who leaves feedback
    private Date date;

    private String eventDescription;

    public TrainingFeedbackDTO() {
    }

    public TrainingFeedbackDTO(TrainingFeedback feedback) {
        this.id = feedback.getId();
        this.trainingId = feedback.getTraining().getId();
        this.understandable = feedback.isUnderstandable();
        this.interesting = feedback.isInteresting();
        this.newKnowledge = feedback.isNewKnowledge();
        this.effectiveness = feedback.getEffectiveness();
        this.studyWithTrainer = feedback.isStudyWithTrainer();
        this.recommend = feedback.isRecommend();
        this.otherInfo = feedback.getOtherInfo();
        this.feedbackerId = feedback.getFeedbacker().getId();
        this.date = feedback.getDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public boolean isUnderstandable() {
        return understandable;
    }

    public void setUnderstandable(boolean understandable) {
        this.understandable = understandable;
    }

    public boolean isInteresting() {
        return interesting;
    }

    public void setInteresting(boolean interesting) {
        this.interesting = interesting;
    }

    public boolean isNewKnowledge() {
        return newKnowledge;
    }

    public void setNewKnowledge(boolean newKnowledge) {
        this.newKnowledge = newKnowledge;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }

    public boolean isStudyWithTrainer() {
        return studyWithTrainer;
    }

    public void setStudyWithTrainer(boolean studyWithTrainer) {
        this.studyWithTrainer = studyWithTrainer;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public long getFeedbackerId() {
        return feedbackerId;
    }

    public void setFeedbackerId(long feedbackerId) {
        this.feedbackerId = feedbackerId;
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

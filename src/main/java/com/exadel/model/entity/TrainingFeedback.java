package com.exadel.model.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

public class TrainingFeedback {
    private String id;
    private Training training;
    private boolean understandable;
    private boolean interesting;
    private boolean newKnowledge;
    @Min(1)
    @Max(5)
    private int effectiveness;
    private boolean studyWithTrainer;
    private boolean recommend;
    private String otherInfo;
    private User feedbacker;      //who leaves feedback
    private Date date;

    public TrainingFeedback() {
    }

    public TrainingFeedback(String id, Training training, boolean understandable, boolean interesting, boolean newKnowledge, int effectiveness,
                            boolean studyWithTrainer, boolean recommend, String otherInfo, User feedbacker, Date date) {
        this.id = id;
        this.training = training;
        this.understandable = understandable;
        this.interesting = interesting;
        this.newKnowledge = newKnowledge;
        this.effectiveness = effectiveness;
        this.studyWithTrainer = studyWithTrainer;
        this.recommend = recommend;
        this.otherInfo = otherInfo;
        this.feedbacker = feedbacker;
        this.date = date;
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

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

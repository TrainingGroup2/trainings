package com.exadel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "training_feedbacks")
public class TrainingFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    @JsonIgnore
    private Training training;

    private boolean understandable;
    private boolean interesting;

    @Column(name = "new_knowledge")
    private boolean newKnowledge;

    @Size(max = 5, min = 0)
    @NotNull
    private int effectiveness;

    @Column(name = "study_with_trainer")
    private boolean studyWithTrainer;
    private boolean recommend;

    @Column(name = "other_info")
    private String otherInfo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User feedbacker; // who leaves feedback

    private Date date;

    public TrainingFeedback() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TrainingFeedback{" +
                "id=" + id +
                ", understandable=" + understandable +
                ", training=" + training +
                ", interesting=" + interesting +
                ", newKnowledge=" + newKnowledge +
                ", effectiveness=" + effectiveness +
                ", studyWithTrainer=" + studyWithTrainer +
                ", recommend=" + recommend +
                ", otherInfo='" + otherInfo + '\'' +
                ", feedbacker=" + feedbacker +
                ", date=" + date +
                '}';
    }
}

package com.exadel.model.entity.feedback;

import com.exadel.dto.TrainingFeedbackDTO;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "training_feedbacks")
public class TrainingFeedback {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    private boolean understandable;
    private boolean interesting;

    @Column(name = "new_knowledge")
    private boolean newKnowledge;

    //@Size(max = 5, min = 0) todo: with this we get EXCEPTION on Integer.
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

    public TrainingFeedback() {}

    public TrainingFeedback(long id) {
        this.id = id;
    }

    public TrainingFeedback(TrainingFeedbackDTO feedbackDTO) {
        this.understandable = feedbackDTO.isUnderstandable();
        this.interesting = feedbackDTO.isInteresting();
        this.newKnowledge = feedbackDTO.isNewKnowledge();
        this.effectiveness = feedbackDTO.getEffectiveness();
        this.studyWithTrainer = feedbackDTO.isStudyWithTrainer();
        this.recommend = feedbackDTO.isRecommend();
        this.otherInfo = feedbackDTO.getOtherInfo();
        this.date = feedbackDTO.getDate();

        this.training = new Training();
        this.training.setId(feedbackDTO.getTrainingId());
        this.feedbacker = new User();
        this.feedbacker.setId(feedbackDTO.getFeedbackerId());
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

package com.exadel.model.entity;

import com.exadel.model.constants.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@DiscriminatorValue(value = "1")
public class Employee extends User {

    @ManyToMany(mappedBy = "participants")
    @Column(name = "visiting_Trainings")
    @JsonIgnore
    private List<Training> visitingTrainings;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    @Column(name = "mentoring_trainings")
    @JsonIgnore
    private List<Training> mentoringTrainings;

    @OneToMany(mappedBy = "feedbacker", cascade = CascadeType.ALL)
    @Column(name = "written_feedbacks")
    @JsonIgnore
    private List<TrainingFeedback> writtenFeedbacks;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    @Column(name = "received_feedbacks")
    @JsonIgnore
    private List<UserFeedback> receivedFeedbacks;

    public Employee() {
        super.setRole(UserRole.EMPLOYEE);
    }

    public List<Training> getVisitingTrainings() {
        return visitingTrainings;
    }

    public void setVisitingTrainings(List<Training> visitingTrainings) {
        this.visitingTrainings = visitingTrainings;
    }

    public List<Training> getMentoringTrainings() {
        return mentoringTrainings;
    }

    public void setMentoringTrainings(List<Training> mentoringTrainings) {
        this.mentoringTrainings = mentoringTrainings;
    }

    public List<TrainingFeedback> getWrittenFeedbacks() {
        return writtenFeedbacks;
    }

    public void setWrittenFeedbacks(List<TrainingFeedback> writtenFeedbacks) {
        this.writtenFeedbacks = writtenFeedbacks;
    }

    public List<UserFeedback> getReceivedFeedbacks() {
        return receivedFeedbacks;
    }

    public void setReceivedFeedbacks(List<UserFeedback> receivedFeedbacks) {
        this.receivedFeedbacks = receivedFeedbacks;
    }
}

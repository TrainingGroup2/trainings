package com.exadel.model.entity;

import com.exadel.model.constants.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "1")
public class Employee extends ExternalTrainer {

    @ManyToMany(mappedBy = "participants")
    //@Column(name = "visiting_trainings")
    @JsonIgnore
    private List<Training> visitingTrainings;

    @OneToMany(mappedBy = "feedbacker", cascade = CascadeType.ALL)
    //@Column(name = "written_feedbacks")
    @JsonIgnore
    private List<TrainingFeedback> writtenFeedbacks;

    public Employee() {
        super.setRole(UserRole.EMPLOYEE);
    }

    public List<Training> getVisitingTrainings() {
        return visitingTrainings;
    }

    public void setVisitingTrainings(List<Training> visitingTrainings) {
        this.visitingTrainings = visitingTrainings;
    }

    public List<TrainingFeedback> getWrittenFeedbacks() {
        return writtenFeedbacks;
    }

    public void setWrittenFeedbacks(List<TrainingFeedback> writtenFeedbacks) {
        this.writtenFeedbacks = writtenFeedbacks;
    }
}

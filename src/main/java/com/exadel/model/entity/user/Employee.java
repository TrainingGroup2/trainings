package com.exadel.model.entity.user;

import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.training.Training;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "1")
public class Employee extends ExternalTrainer {

    @ManyToMany(mappedBy = "participants")
    private List<Training> visitingTrainings;

    @OneToMany(mappedBy = "feedbacker", cascade = CascadeType.ALL)
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

package com.exadel.model.entity.user;

import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.feedback.UserFeedback;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Виктория on 13.07.2015.
 */
@Entity
@DiscriminatorValue(value = "2")
public class ExternalTrainer extends User {

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Training> mentoringTrainings;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private Set<UserFeedback> receivedFeedbacks;

    public ExternalTrainer() {
        super.setRole(UserRole.EXTERNAL_TRAINER);
    }
    @JsonIgnore
    public List<Training> getMentoringTrainings() {
        return mentoringTrainings;
    }

    public void setMentoringTrainings(List<Training> mentoringTrainings) {
        this.mentoringTrainings = mentoringTrainings;
    }

    public Set<UserFeedback> getReceivedFeedbacks() {
        return receivedFeedbacks;
    }

    public void setReceivedFeedbacks(Set<UserFeedback> receivedFeedbacks) {
        this.receivedFeedbacks = receivedFeedbacks;
    }
}

package com.exadel.model.entity.user;

import com.exadel.model.entity.training.Training;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Виктория on 13.07.2015.
 */
@Entity
@DiscriminatorValue(value = "3")
public class ExternalVisitor extends User {
    private static final long serialVersionUID = 1L;

    @ManyToMany(mappedBy = "participants")
    //@Column(name = "visiting_Trainings")
    private List<Training> visitingTrainings;

    public ExternalVisitor() {
        super.setRole(UserRole.EXTERNAL_VISITOR);
    }

    public List<Training> getVisitingTrainings() {
        return visitingTrainings;
    }

    public void setVisitingTrainings(List<Training> visitingTrainings) {
        this.visitingTrainings = visitingTrainings;
    }
}

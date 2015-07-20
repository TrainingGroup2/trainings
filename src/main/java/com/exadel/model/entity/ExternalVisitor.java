package com.exadel.model.entity;

import com.exadel.model.constants.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Виктория on 13.07.2015.
 */
@Entity
@Table(name = "users")
@DiscriminatorValue(value = "3")
public class ExternalVisitor extends User {

    @ManyToMany(mappedBy = "participants")
    @Column(name = "visiting_Trainings")
    @JsonIgnore
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

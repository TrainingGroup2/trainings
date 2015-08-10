package com.exadel.model.entity.user;

import com.exadel.dto.UserDTO;
import com.exadel.model.entity.training.Training;

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

    @ManyToMany(mappedBy = "participants")
    private List<Training> visitingTrainings;

    public ExternalVisitor() {
        super.setRole(UserRole.EXTERNAL_VISITOR);
    }

    public ExternalVisitor(UserDTO userDTO) {
        super(userDTO);
        super.setRole(UserRole.EXTERNAL_VISITOR);
    }

    public List<Training> getVisitingTrainings() {
        return visitingTrainings;
    }

    public void setVisitingTrainings(List<Training> visitingTrainings) {
        this.visitingTrainings = visitingTrainings;
    }
}

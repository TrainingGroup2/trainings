package com.exadel.model.entity;

import java.util.List;

/**
 * Created by Виктория on 13.07.2015.
 */
public class ExternalVisitor extends User {
    private List<Training> visitingTrainings;

    public ExternalVisitor(String id, String name, String secondName, String surname, String phone, String email, List<Training> visitingTrainings) {
        super(id, name, secondName, surname, phone, email);
        this.visitingTrainings = visitingTrainings;
    }

    public ExternalVisitor() {
        super();
    }

    public List<Training> getVisitingTrainings() {
        return visitingTrainings;
    }

    public void setVisitingTrainings(List<Training> visitingTrainings) {
        this.visitingTrainings = visitingTrainings;
    }
}

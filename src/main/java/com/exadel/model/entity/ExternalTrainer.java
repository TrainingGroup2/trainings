package com.exadel.model.entity;

import java.util.List;

/**
 * Created by Виктория on 13.07.2015.
 */
public class ExternalTrainer extends User {
    private List<Training> mentoringTrainings;

    public ExternalTrainer() {
        super();
    }

    public ExternalTrainer(String id, String name, String surname, String phone, String email,List<Training> mentoringTrainings) {
        super(id, name, surname, phone, email);
        this.mentoringTrainings= mentoringTrainings;
    }

    public List<Training> getMentoringTrainings() {
        return mentoringTrainings;
    }

    public void setMentoringTrainings(List<Training> mentoringTrainings) {
        this.mentoringTrainings = mentoringTrainings;
    }
}

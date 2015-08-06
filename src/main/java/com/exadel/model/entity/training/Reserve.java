package com.exadel.model.entity.training;

import com.exadel.model.entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "training_reserve")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @ManyToOne
    @JoinColumn(name = "reservist_id", nullable = false)
    private User reservist;

    public Reserve() {
    }

    public Reserve(Training training, User reservist) {
        this.training = training;
        this.reservist = reservist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public User getReservist() {
        return reservist;
    }

    public void setReservist(User reservist) {
        this.reservist = reservist;
    }
}

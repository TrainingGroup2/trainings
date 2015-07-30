package com.exadel.model.entity.events;

import com.exadel.dto.EventDTO;
import com.exadel.model.entity.training.Training;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "training_events")
public class TrainingEvent extends Event{
    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    Training training;

    public TrainingEvent() {
        super();
    }

    @Override
    public EventDTO toEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(this.getId());
        eventDTO.setSubjectId(this.training.getId());
        eventDTO.setIsWatched(this.isWatched());
        eventDTO.setDescription(this.getDescription());
        eventDTO.setDate(this.getDate());

        eventDTO.setEventType(EventType.TRAINING);
        return eventDTO;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}

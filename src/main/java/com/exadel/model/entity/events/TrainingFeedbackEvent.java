package com.exadel.model.entity.events;

import com.exadel.dto.EventDTO;
import com.exadel.model.entity.feedback.TrainingFeedback;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "training_feedback_events")
public class TrainingFeedbackEvent extends Event {
    @OneToOne
    @JoinColumn(name = "training_feedback_id", nullable = false)
    TrainingFeedback trainingFeedback;

    public TrainingFeedbackEvent() {
        super();
    }

    @Override
    public EventDTO toEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(this.getId());
        eventDTO.setSubjectId(this.trainingFeedback.getId());
        eventDTO.setIsWatched(this.isWatched());
        eventDTO.setDescription(this.getDescription());
        eventDTO.setDate(this.getDate());

        eventDTO.setEventType(EventType.TRAINING_FEEDBACK);
        return eventDTO;
    }

    public TrainingFeedback getTrainingFeedback() {
        return trainingFeedback;
    }

    public void setTrainingFeedback(TrainingFeedback trainingFeedback) {
        this.trainingFeedback = trainingFeedback;
    }
}

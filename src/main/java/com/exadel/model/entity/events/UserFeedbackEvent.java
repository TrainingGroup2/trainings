package com.exadel.model.entity.events;

import com.exadel.dto.EventDTO;
import com.exadel.model.entity.feedback.UserFeedback;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_feedback_events")
public class UserFeedbackEvent extends Event {
    @OneToOne
    @JoinColumn(name = "user_feedback_id", nullable = false)
    private UserFeedback userFeedback;

    public UserFeedbackEvent() {
        super();
    }

    @Override
    public EventDTO toEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(this.getId());
        eventDTO.setSubjectId(this.userFeedback.getId());
        eventDTO.setIsWatched(this.isWatched());
        eventDTO.setDescription(this.getDescription());
        eventDTO.setDate(this.getDate());

        eventDTO.setEventType(EventType.USER_FEEDBACK);
        return eventDTO;
    }

    public UserFeedback getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(UserFeedback userFeedback) {
        this.userFeedback = userFeedback;
    }
}

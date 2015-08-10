package com.exadel.dto;

import com.exadel.model.entity.events.*;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.model.entity.training.Training;

import java.util.Date;

public class EventDTO{
    private long id;
    private boolean isWatched;
    private String description;
    private Date date;
    private long subjectId;      //id of Training/UserFeedback/TrainingFeedback
    private EventType eventType;

    public EventDTO() {}

    public TrainingEvent toTrainingEvent(){
        TrainingEvent event = new TrainingEvent();
        event.setId(this.id);
        event.setIsWatched(this.isWatched);
        event.setDescription(this.description);
        event.setDate(this.date);
        event.setTraining(new Training(this.subjectId));
        return event;
    }

    public TrainingFeedbackEvent toTrainingFeedbackEvent(){
        TrainingFeedbackEvent event = new TrainingFeedbackEvent();
        event.setId(this.id);
        event.setIsWatched(this.isWatched);
        event.setDescription(this.description);
        event.setDate(this.date);
        event.setTrainingFeedback(new TrainingFeedback(this.subjectId));
        return event;
    }

    public UserFeedbackEvent toUserFeedbackEvent(){
        UserFeedbackEvent event = new UserFeedbackEvent();
        event.setId(this.id);
        event.setIsWatched(this.isWatched);
        event.setDescription(this.description);
        event.setDate(this.date);
        event.setUserFeedback(new UserFeedback(this.subjectId));
        return event;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setIsWatched(boolean isWatched) {
        this.isWatched = isWatched;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

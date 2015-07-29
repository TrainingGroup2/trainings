package com.exadel.service.events;

import com.exadel.model.entity.events.TrainingFeedbackEvent;

import java.util.Collection;
import java.util.Optional;

public interface TrainingFeedbackEventService {
    TrainingFeedbackEvent getEventById(String id);

    Collection<TrainingFeedbackEvent> getAllEvents();

    Collection<TrainingFeedbackEvent> getUnwatchedEvents();

    Optional<TrainingFeedbackEvent> addEvent(TrainingFeedbackEvent event);

    void deleteById(String id);
}

package com.exadel.service.events;

import com.exadel.model.entity.events.TrainingEvent;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainingEventService {
    TrainingEvent getEventById(String id);

    Collection<TrainingEvent> getAllEvents();

    Collection<TrainingEvent> getUnwatchedEvents();

    Optional<TrainingEvent> addEvent(TrainingEvent event);

}

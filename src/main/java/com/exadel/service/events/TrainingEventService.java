package com.exadel.service.events;

import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.events.TrainingFeedbackEvent;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainingEventService {
    TrainingEvent getEventById(String id);

    Collection<TrainingEvent> getAllEvents();

    Collection<TrainingEvent> getUnwatchedEvents();

    Optional<TrainingEvent> addEvent(TrainingEvent event);

    Page<TrainingEvent> getTrainingEvents(Integer first, Integer size);

}

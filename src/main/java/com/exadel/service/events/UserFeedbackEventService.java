package com.exadel.service.events;

import com.exadel.model.entity.events.UserFeedbackEvent;

import java.util.Collection;
import java.util.Optional;

public interface UserFeedbackEventService {
    UserFeedbackEvent getEventById(String id);

    Collection<UserFeedbackEvent> getAllEvents();

    Collection<UserFeedbackEvent> getUnwatchedEvents();

    Optional<UserFeedbackEvent> addEvent(UserFeedbackEvent event);
}

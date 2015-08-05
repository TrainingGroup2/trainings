package com.exadel.service.events;

import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.model.entity.events.UserFeedbackEvent;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserFeedbackEventService {
    UserFeedbackEvent getEventById(String id);

    Collection<UserFeedbackEvent> getAllEvents();

    Collection<UserFeedbackEvent> getUnwatchedEvents();

    Optional<UserFeedbackEvent> addEvent(UserFeedbackEvent event);


}

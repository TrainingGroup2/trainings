package com.exadel.service.impl.events;

import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.user.User;
import com.exadel.repository.events.UserFeedbackEventRepository;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserFeedbackEventServiceImpl implements UserFeedbackEventService {
    @Autowired
    private UserFeedbackEventRepository eventRepository;

    @Override
    public UserFeedbackEvent getEventById(String id) {
        UserFeedbackEvent event = eventRepository.findOne(Long.parseLong(id));
        if (event != null) {
            return event;
        }
        else {
            return null;   //TODO: THROW EXCEPTION
        }
    }

    @Override
    public Collection<UserFeedbackEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Collection<UserFeedbackEvent> getUnwatchedEvents() {
        return eventRepository.findByIsWatchedFalse();
    }

    @Override
    public Optional<UserFeedbackEvent> addEvent(UserFeedbackEvent event) {
        return Optional.ofNullable(eventRepository.saveAndFlush(event));
    }

}

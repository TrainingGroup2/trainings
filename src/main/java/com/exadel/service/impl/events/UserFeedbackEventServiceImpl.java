package com.exadel.service.impl.events;

import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.repository.events.UserFeedbackEventRepository;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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

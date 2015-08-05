package com.exadel.service.impl.events;

import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.repository.events.TrainingEventRepository;
import com.exadel.service.events.TrainingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingEventServiceImpl implements TrainingEventService {
    @Autowired
    private TrainingEventRepository eventRepository;

    @Override
    public TrainingEvent getEventById(String id) {
        TrainingEvent event = eventRepository.findOne(Long.parseLong(id));
        if (event != null) {
            return event;
        }
        else {
            return null;   //TODO: THROW EXCEPTION
        }
    }

    @Override
    public Collection<TrainingEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Collection<TrainingEvent> getUnwatchedEvents() {
        return eventRepository.findByIsWatchedFalse();
    }

    @Override
    public Optional<TrainingEvent> addEvent(TrainingEvent event) {
        return Optional.ofNullable(eventRepository.saveAndFlush(event));
    }

}

package com.exadel.service.impl.events;

import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.training.Training;
import com.exadel.repository.events.TrainingFeedbackEventRepository;
import com.exadel.service.events.TrainingFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingFeedbackEventImpl implements TrainingFeedbackEventService {
    @Autowired
    private TrainingFeedbackEventRepository eventRepository;

    @Override
    public TrainingFeedbackEvent getEventById(String id) {
        TrainingFeedbackEvent event = eventRepository.findOne(Long.parseLong(id));
        if (event != null) {
            return event;
        }
        else {
            return null;   //TODO: THROW EXCEPTION
        }
    }

    @Override
    public Collection<TrainingFeedbackEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Collection<TrainingFeedbackEvent> getUnwatchedEvents() {
        return eventRepository.findByIsWatchedFalse();
    }

    @Override
    public Optional<TrainingFeedbackEvent> addEvent(TrainingFeedbackEvent event) {
        return Optional.ofNullable(eventRepository.saveAndFlush(event));
    }

    @Override
    public void deleteById(long id) {
        eventRepository.delete(id);
    }

    @Override
    public void deleteByTrainingFeedbackId(long id) {
        eventRepository.deleteByTrainingFeedbackId(id);
    }

    @Override
    public Page<TrainingFeedbackEvent> getTrainingFeedbackEvents(Integer first, Integer size) {
        Integer pageNumber = first / size;
        PageRequest request =
                new PageRequest(pageNumber, size);
        return eventRepository.findAll(request);
    }

}

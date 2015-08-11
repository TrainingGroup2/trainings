package com.exadel.controller;

import com.exadel.dto.EventDTO;
import com.exadel.model.entity.events.*;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;
import com.exadel.search.EventSearch;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import com.google.common.collect.Lists;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/events", headers = "Accept=application/json")
public class EventController {
    @Autowired
    private TrainingEventService trainingEventService;
    @Autowired
    private TrainingFeedbackEventService trainingFeedbackEventService;
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;
    @Autowired
    EventSearch eventSearch;

    public static Map<DeferredResult<List<EventDTO>>, Integer> eventRequests =
            new ConcurrentHashMap<DeferredResult<List<EventDTO>>, Integer>();
    @PreAuthorize("hasRole('0')")
    @RequestMapping(method = RequestMethod.GET)
    public Set<EventDTO> getEvents() {
        Set<EventDTO> eventDTOs = new TreeSet<>((event1, event2) -> {
            int r = event2.getDate().compareTo(event1.getDate());
            return (r!=0) ? r :1;
        });
        List<Event> events = new ArrayList<>();
        events.addAll(trainingEventService.getAllEvents());
        events.addAll(trainingFeedbackEventService.getAllEvents());
        events.addAll(userFeedbackEventService.getAllEvents());
        for (Event event: events){
            eventDTOs.add(event.toEventDTO());
        }
        return eventDTOs;
    }

    public List<EventDTO> getAll() {
        List<Event> events = new ArrayList<>();
        events.addAll(trainingEventService.getUnwatchedEvents());
        events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
        events.addAll(userFeedbackEventService.getUnwatchedEvents());
        List<EventDTO> list = new ArrayList<>();
        for (Event event: events){
            list.add(event.toEventDTO());
        }
        return list;

    }

    @RequestMapping(value = "/unwatched", method = RequestMethod.GET)
    public DeferredResult<List<EventDTO>> getUnwatchedEvents(@RequestParam int eventIndex) {
        final DeferredResult<List<EventDTO>> deferredResult = new DeferredResult<List<EventDTO>>(null, Collections.emptyList());
        this.eventRequests.put(deferredResult, eventIndex);
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                eventRequests.remove(deferredResult);
            }
        });

        Set<EventDTO> eventDTOs = new TreeSet<>((event1, event2) -> {
            int r = event2.getDate().compareTo(event1.getDate());
            return (r!=0) ? r :1;
        });
        List<EventDTO> result = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        events.addAll(trainingEventService.getUnwatchedEvents());
        events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
        events.addAll(userFeedbackEventService.getUnwatchedEvents());
        for (Event event: events){
            eventDTOs.add(event.toEventDTO());
        }
        result.addAll(eventDTOs);
        List <Event> updates = new ArrayList<>();
        if(eventIndex<=events.size()) {
            updates = events.subList(eventIndex, events.size());
        }
        if (updates.size()!=0) {
            deferredResult.setResult(result);
        }

        return deferredResult;
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/watched", method = RequestMethod.PUT)
    public void updateEvent(@RequestBody EventDTO eventDTO) {
        eventDTO.setIsWatched(true);
        if (eventDTO.getEventType()== EventType.TRAINING){
            trainingEventService.addEvent(eventDTO.toTrainingEvent());
        }
        else if (eventDTO.getEventType()== EventType.TRAINING_FEEDBACK){
            trainingFeedbackEventService.addEvent(eventDTO.toTrainingFeedbackEvent());
        }
        else {
            userFeedbackEventService.addEvent(eventDTO.toUserFeedbackEvent());
        }
        for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
            List<Event> events = new ArrayList<>();
            events.addAll(trainingEventService.getUnwatchedEvents());
            events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
            events.addAll(userFeedbackEventService.getUnwatchedEvents());
            List<EventDTO> result = new ArrayList<>();
            for (Event event: events){
                result.add(event.toEventDTO());
            }
            entry.getKey().setResult(result);
        }

    }


}

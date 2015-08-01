package com.exadel.controller;

import com.exadel.dto.EventDTO;
import com.exadel.model.entity.events.Event;
import com.exadel.model.entity.events.EventType;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@PreAuthorize("hasRole('0')")
@RequestMapping(value = "/events", headers = "Accept=application/json")
public class EventController {
    @Autowired
    private TrainingEventService trainingEventService;
    @Autowired
    private TrainingFeedbackEventService trainingFeedbackEventService;
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;

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


    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/unwatched", method = RequestMethod.GET)
    public Set<EventDTO> getUnwatchedEvents() {
        Set<EventDTO> eventDTOs = new TreeSet<>((event1, event2) -> {
            int r = event2.getDate().compareTo(event1.getDate());
            return (r!=0) ? r :1;
        });
        List<Event> events = new ArrayList<>();
        events.addAll(trainingEventService.getUnwatchedEvents());
        events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
        events.addAll(userFeedbackEventService.getUnwatchedEvents());
        for (Event event: events){
            eventDTOs.add(event.toEventDTO());
        }
        return eventDTOs;
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
        //TODO: EXCEPTION
    }
}

package com.exadel.controller;

import com.exadel.dto.EventDTO;
import com.exadel.model.entity.events.Event;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/events", headers = "Accept=application/json")
public class EventController {
    @Autowired
    private TrainingEventService trainingEventService;
    @Autowired
    private TrainingFeedbackEventService trainingFeedbackEventService;
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;

    @RequestMapping(method = RequestMethod.GET)
    public List<EventDTO> getTrainings() {
        List<EventDTO> eventDTOs = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        events.addAll((List)trainingEventService.getAllEvents());
        events.addAll((List)trainingFeedbackEventService.getAllEvents());
        events.addAll((List)userFeedbackEventService.getAllEvents());
        for (Event event: events){
            eventDTOs.add(event.toEventDTO());
        }
        //TODO: SORT
        return eventDTOs;
    }

    @RequestMapping(value = "/unwatched", method = RequestMethod.GET)
    public List<EventDTO> getUnwatchedTrainings() {
        List<EventDTO> eventDTOs = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        events.addAll((List)trainingEventService.getUnwatchedEvents());
        events.addAll((List)trainingFeedbackEventService.getUnwatchedEvents());
        events.addAll((List)userFeedbackEventService.getUnwatchedEvents());
        for (Event event: events){
            eventDTOs.add(event.toEventDTO());
        }
        //TODO: SORT
        return eventDTOs;
    }
}

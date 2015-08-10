package com.exadel.controller;

import com.exadel.dto.EntryDTO;
import com.exadel.dto.EventDTO;
import com.exadel.dto.TrainingDTO;
import com.exadel.dto.UserDTO;
import com.exadel.model.entity.events.Event;
import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.model.entity.user.UserRole;
import com.exadel.model.entity.user.User;
import com.exadel.search.TrainingSearch;
import com.exadel.search.UserSearch;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import com.exadel.service.events.TrainingEventService;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import com.exadel.util.UserUtil;
import com.google.common.collect.Lists;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.exadel.Utils.addWeekToDate;
import static com.exadel.Utils.getDayNumberToAdd;
import com.exadel.service.events.TrainingFeedbackEventService;
import com.exadel.service.events.UserFeedbackEventService;
import org.springframework.web.context.request.async.DeferredResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('0','1','2')")
@RequestMapping(value = "/trainings", headers = "Accept=application/json")
public class TrainingsController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainingEventService trainingEventService;
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    private EmailMessages emailMessages;
    @Autowired
    TrainingSearch trainingSearch;

    public static int size;

    @PersistenceContext
    private EntityManager entityManager;

    private TrainingFeedbackEventService trainingFeedbackEventService;
    @Autowired
    private UserFeedbackEventService userFeedbackEventService;

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingDTO> getTrainings() {
        List<Training> trainings = (List<Training>) trainingService.getAllTrainings();
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }


    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)   //called only by ADMIN
    public Training createTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = new Training(trainingDTO);
        if (UserUtil.hasRole(0)) {
            training.setStatus(TrainingStatus.APPROVED);
            training = trainingService.addTraining(training);
        }
        else {
            training.setStatus(TrainingStatus.DRAFTED);
            training = trainingService.addTraining(training);
            trainingDTO.setId(training.getId());
            trainingDTO.setEventDescription(emailMessages.newTrainingToAdmin(training));
            smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Changes in Trainings", emailMessages.newTrainingToAdmin(training));
            trainingEventService.addEvent(new TrainingEvent(trainingDTO));
            List<EventDTO> eventDTOs = new ArrayList<>();

            List<Event> events = new ArrayList<>();
            events.addAll(trainingEventService.getUnwatchedEvents());
            events.addAll(trainingFeedbackEventService.getUnwatchedEvents());
            events.addAll(userFeedbackEventService.getUnwatchedEvents());

            for (Event event: events){
                eventDTOs.add(event.toEventDTO());
            }
            for (Map.Entry<DeferredResult<List<EventDTO>>, Integer> entry : EventController.eventRequests.entrySet()) {
                entry.getKey().setResult(eventDTOs);
            }
        }

        if (trainingDTO.isRepeated()) {
            generateEntries(trainingDTO.getBegin(), trainingDTO.getEnd(),
                    training, trainingDTO.getEntries());
        } else {
            List<EntryDTO> entryDTOs = trainingDTO.getEntries();
            Collections.sort(entryDTOs);
            for (EntryDTO entryDTO : entryDTOs) {
                Entry entry = new Entry(entryDTO);
                entry.setTraining(training);
                entryService.addEntry(entry);
            }
        }

        return training;
    }

    void generateEntries(Date beginDay, Date endDay, Training training, List<EntryDTO> entryDTOs) {
        if (beginDay == null || endDay == null)
            return;

        Calendar begin = new GregorianCalendar();
        begin.setTime(beginDay);
        int beginDayOfWeek = begin.get(Calendar.DAY_OF_WEEK);

        Calendar generator = new GregorianCalendar();

        for (EntryDTO entryDTO : entryDTOs) {
            generateTime(generator, entryDTO.getBeginTime(), begin);
            generator.add(Calendar.DATE, getDayNumberToAdd(beginDayOfWeek, entryDTO.getDayOfWeek()));
            entryDTO.setBeginTime(generator.getTime());

            generateTime(generator, entryDTO.getEndTime(), begin);
            generator.add(Calendar.DATE, getDayNumberToAdd(beginDayOfWeek, entryDTO.getDayOfWeek()));
            entryDTO.setEndTime(generator.getTime());
        }

        Collections.sort(entryDTOs);
        Queue<EntryDTO> duplicates =  new LinkedList<>(entryDTOs);
        EntryDTO entryDTO = duplicates.remove();

        while (entryDTO.getBeginTime().before(endDay)) {
            Entry entry = new Entry(entryDTO);
            entry.setTraining(training);
            entryService.addEntry(entry);

            entryDTO.setEndTime(addWeekToDate(entryDTO.getEndTime()));
            entryDTO.setBeginTime(addWeekToDate(entryDTO.getBeginTime()));
            duplicates.add(entryDTO);

            entryDTO = duplicates.remove();
        }
    }

    private void generateTime(Calendar calendar, Date time, Calendar beginDay) {
        calendar.clear();
        calendar.setTime(time);
        calendar.set(beginDay.get(Calendar.YEAR), beginDay.get(Calendar.MONTH),
                beginDay.get(Calendar.DAY_OF_MONTH));
    }




    @RequestMapping(value = "/pages/count/{pageNumber}", method = RequestMethod.GET)
    public Integer getCount(@PathVariable Integer pageNumber, @RequestParam Integer size,String sortParam, @RequestParam (required = false) boolean isReversed) {
        return this.size;
    }

    @PreAuthorize("hasAnyRole('0','1','2')")
    @RequestMapping(value ="/pages/{pageNumber}",method = RequestMethod.GET)
    public List<TrainingDTO> getTrainingsPaging(@PathVariable Integer pageNumber, @RequestParam Integer size,
                                                @RequestParam (required = false)String name,
                                                @RequestParam (required = false)String trainer,
                                                @RequestParam (required = false)String targetAudience,
                                                @RequestParam (required = false) boolean isReversed,
                                                @RequestParam (required = false) String sortParam) {
        this.size=0;
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Training.class).get();
        List<Training> results = new ArrayList<>();

       Query query = null;
        Query trainerNameQuery ;
      Query targetAudienceQuery;
        Query nameQuery;
        if(trainer!= null) {
            trainerNameQuery = trainingSearch.searchByTrainerName(trainer);
            query = queryBuilder.bool().must(trainerNameQuery).createQuery();
        }
        if(targetAudience!=null) {
             targetAudienceQuery = trainingSearch.searchByTargetAudience(targetAudience);
            if (query != null) {
                query = queryBuilder.bool().must(query).must(targetAudienceQuery).createQuery();
            } else
                query = queryBuilder.bool().must(targetAudienceQuery).createQuery();
        }
        if(name != null) {
            nameQuery = trainingSearch.searchByName(name);
            if (query != null) {
                query = queryBuilder.bool().must(query).must(nameQuery).createQuery();
            } else
                query = queryBuilder.bool().must(nameQuery).createQuery();
        }

        if(query!=null) {
            FullTextQuery jpaQuery =
                    fullTextEntityManager.createFullTextQuery(query, Training.class);
            jpaQuery.setFirstResult(pageNumber);
            jpaQuery.setMaxResults(size);
            if(sortParam!=null) {
                 Sort sort = new Sort(
                        new SortField(sortParam, SortField.Type.STRING));
                jpaQuery.setSort(sort);
            }
            this.size=jpaQuery.getResultList().size();
            if(this.size<size) {
                this.size=1;
            }
            else
            this.size = this.size/size;
            size = jpaQuery.getResultSize();
            results=jpaQuery.getResultList();

            if(isReversed) {
                results = Lists.reverse(results);
            }
        }
        if(name == null && trainer ==null && targetAudience==null) {

            Page<Training> page = trainingService.getTrainings(pageNumber, size,sortParam,isReversed);
            results = page.getContent();
            this.size = page.getTotalPages();
        }
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : results) {
            trainingDTOs.add(new TrainingDTO(training));
        }
        return trainingDTOs;
    }

    }

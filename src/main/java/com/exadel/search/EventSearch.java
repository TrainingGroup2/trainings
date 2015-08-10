package com.exadel.search;

import com.exadel.model.entity.events.Event;
import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.user.User;
import com.google.common.collect.Lists;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.SessionFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EventSearch {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;

    public List<TrainingEvent> getByDescriptionFromTrainingEvents(String name) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(TrainingEvent.class).get();
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("description")
                        .matching(name)
                        .createQuery();
        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query,TrainingEvent.class);


        @SuppressWarnings("unchecked")
        List<TrainingEvent> results = jpaQuery.getResultList();
        return results;
    }

    public List<TrainingFeedbackEvent> getByDescriptionFromTrainingFeedbackEvents(String name) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(TrainingFeedbackEvent.class).get();
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("description")
                        .matching(name)
                        .createQuery();
        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query,TrainingFeedbackEvent.class);
        @SuppressWarnings("unchecked")
        List<TrainingFeedbackEvent> results = jpaQuery.getResultList();
        return results;
    }

    public List<UserFeedbackEvent> getByDescriptionFromUserFeedbackEvents(String name) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(UserFeedbackEvent.class).get();
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("description")
                        .matching(name)
                        .createQuery();
        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query,UserFeedbackEvent.class);

        @SuppressWarnings("unchecked")
        List<UserFeedbackEvent> results = jpaQuery.getResultList();
        return results;
    }
}

package com.exadel.search;

import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class TrainingSearch {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;

    public Query searchByTrainerName(String name) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Training.class).get();

        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("trainer.name","trainer.surname")
                        .matching(name)
                        .createQuery();
        return query;
    }
    public List<Training> searchByTrainerSurame(String surname, String name) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Training.class).get();
//queryBuilder.
        org.apache.lucene.search.Query query = queryBuilder.keyword().onField("trainer.surname").matching(surname).createQuery();

        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query,Training.class);
        @SuppressWarnings("unchecked")
        List<Training> results = jpaQuery.getResultList();
        return results;
    }
    public Query searchByTargetAudience(String text) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Training.class).get();

       Query query =
                queryBuilder
                        .keyword()
                        .onFields("targetAudience")
                        .matching(text)
                        .createQuery();
        return query;
    }
    public Query searchByName(String text) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Training.class).get();

        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("name")
                        .matching(text)
                        .createQuery();
        return query;
    }

    public List<Entry> searchByDate(Date date ) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Entry.class);
        criteria.add(Restrictions.eq("beginTime", date+"%"));
        List <Entry> result =new ArrayList(criteria.list()) ;
        return result;
    }
}

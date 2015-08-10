package com.exadel.search;

import com.exadel.model.entity.events.Event;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;
import org.apache.lucene.document.DateTools;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Collection;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
@Transactional
public class UserSearch {

    public Criterion searchBySurname(String surname) {
        Criterion criterion= Restrictions.like("surname", "%" + surname + "%");
        return criterion;
    }

    public Criterion searchByEmail(String email) {
        Criterion criterion = Restrictions.like("email", "%" + email + "%");
        return criterion;
    }

    public Criterion searchByName(String name) {
        Criterion criterion = Restrictions.like("name", "%" + name + "%");
        return criterion;
    }

    public Criterion searchByPhone(String phone) {
        Criterion criterion =Restrictions.like("phone", "%" + phone + "%");
        return criterion;
    }
}
package com.exadel.model.entity.events;

import com.exadel.dto.EventDTO;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.persistence.Parameter;
import java.util.Date;

@MappedSuperclass
public abstract class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "is_watched")
    private boolean isWatched;

    private String description;

    private Date date;

    public Event(){}

    public abstract EventDTO toEventDTO();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setIsWatched(boolean isWatched) {
        this.isWatched = isWatched;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Event compareTime) {

        int retVal = date.compareTo(compareTime.getDate()) ;

        return retVal;
    }
}

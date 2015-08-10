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
@Indexed
@AnalyzerDef(name = "customanalize",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "language", value = "Russian")
                }),
                @TokenFilterDef(factory = NGramFilterFactory.class,params  ={
                        @org.hibernate.search.annotations.Parameter(name="minGramSize",value="3"), @org.hibernate.search.annotations.Parameter(name = "maxGramSize", value = "100")}),
                @TokenFilterDef(factory =EdgeNGramFilterFactory.class, params  ={
                        @org.hibernate.search.annotations.Parameter(name="minGramSize",value="3"), @org.hibernate.search.annotations.Parameter(name = "maxGramSize", value = "100")})

        })
public abstract class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "is_watched")
    private boolean isWatched;

    @Field
    @Analyzer(definition = "customanalize")
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

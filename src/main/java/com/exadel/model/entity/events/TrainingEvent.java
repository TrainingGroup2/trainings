package com.exadel.model.entity.events;

import com.exadel.dto.EntryDTO;
import com.exadel.dto.EventDTO;
import com.exadel.dto.TrainingDTO;
import com.exadel.model.entity.training.Training;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Indexed
@Table(name = "training_events")
@AnalyzerDef(name = "custom",
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
                        @Parameter(name="minGramSize",value="3"), @Parameter(name = "maxGramSize", value = "100")}),
                @TokenFilterDef(factory =EdgeNGramFilterFactory.class, params  ={
                        @Parameter(name="minGramSize",value="3"), @Parameter(name = "maxGramSize", value = "100")})

        })
public class TrainingEvent extends Event{

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    Training training;

    public TrainingEvent() {
        super();
    }

    public TrainingEvent(TrainingDTO trainingDTO) {
        this.setIsWatched(false);
        this.setDate(new Date());
        this.setDescription(trainingDTO.getEventDescription());
        this.training = new Training(trainingDTO.getId());
    }

    public TrainingEvent(EntryDTO entryDTO) {
        this.setIsWatched(false);
        this.setDate(new Date());
        this.setDescription(entryDTO.getEventDescription());
        this.training = new Training(entryDTO.getTrainingId());
    }

    @Override
    public EventDTO toEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(this.getId());
        eventDTO.setSubjectId(this.training.getId());
        eventDTO.setIsWatched(this.isWatched());
        eventDTO.setDescription(this.getDescription());
        eventDTO.setDate(this.getDate());

        eventDTO.setEventType(EventType.TRAINING);
        return eventDTO;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}

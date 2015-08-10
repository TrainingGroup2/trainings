package com.exadel.model.entity.events;

import com.exadel.dto.EventDTO;
import com.exadel.dto.TrainingFeedbackDTO;
import com.exadel.model.entity.feedback.TrainingFeedback;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Indexed
@Table(name = "training_feedback_events")
@AnalyzerDef(name = "cust",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "language", value = "Russian")
                })

        })
public class TrainingFeedbackEvent extends Event {
    @OneToOne
    @JoinColumn(name = "training_feedback_id", nullable = false)
    TrainingFeedback trainingFeedback;

    public TrainingFeedbackEvent() {
        super();
    }

    public TrainingFeedbackEvent(TrainingFeedbackDTO feedbackDTO) {
        this.setIsWatched(false);
        this.setDate(feedbackDTO.getDate());
        this.setDescription(feedbackDTO.getEventDescription());
        this.trainingFeedback = new TrainingFeedback(feedbackDTO.getId());
    }

    @Override
    public EventDTO toEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(this.getId());
        eventDTO.setSubjectId(this.trainingFeedback.getId());
        eventDTO.setIsWatched(this.isWatched());
        eventDTO.setDescription(this.getDescription());
        eventDTO.setDate(this.getDate());

        eventDTO.setEventType(EventType.TRAINING_FEEDBACK);
        return eventDTO;
    }

    public TrainingFeedback getTrainingFeedback() {
        return trainingFeedback;
    }

    public void setTrainingFeedback(TrainingFeedback trainingFeedback) {
        this.trainingFeedback = trainingFeedback;
    }
}

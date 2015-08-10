package com.exadel.model.entity.events;

import com.exadel.dto.EventDTO;
import com.exadel.dto.UserFeedbackDTO;
import com.exadel.model.entity.feedback.UserFeedback;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Indexed
@Table(name = "user_feedback_events")
@AnalyzerDef(name = "customa",
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
public class UserFeedbackEvent extends Event {
    @OneToOne
    @JoinColumn(name = "user_feedback_id", nullable = false)
    private UserFeedback userFeedback;

    public UserFeedbackEvent() {
        super();
    }

    public UserFeedbackEvent(UserFeedbackDTO feedbackDTO) {
        this.setIsWatched(false);
        this.setDate(feedbackDTO.getDate());
        this.setDescription(feedbackDTO.getEventDescription());
        this.userFeedback = new UserFeedback(feedbackDTO.getId());
    }

    @Override
    public EventDTO toEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(this.getId());
        eventDTO.setSubjectId(this.userFeedback.getId());
        eventDTO.setIsWatched(this.isWatched());
        eventDTO.setDescription(this.getDescription());
        eventDTO.setDate(this.getDate());

        eventDTO.setEventType(EventType.USER_FEEDBACK);
        return eventDTO;
    }

    public UserFeedback getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(UserFeedback userFeedback) {
        this.userFeedback = userFeedback;
    }
}

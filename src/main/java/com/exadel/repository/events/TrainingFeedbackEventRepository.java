package com.exadel.repository.events;

import com.exadel.model.entity.events.TrainingFeedbackEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface TrainingFeedbackEventRepository extends JpaRepository<TrainingFeedbackEvent, Long> {
    Collection<TrainingFeedbackEvent> findByIsWatchedFalse();
}

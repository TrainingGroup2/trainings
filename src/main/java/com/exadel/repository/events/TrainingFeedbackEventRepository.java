package com.exadel.repository.events;

import com.exadel.model.entity.events.TrainingFeedbackEvent;
import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.training.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface TrainingFeedbackEventRepository extends JpaRepository<TrainingFeedbackEvent, Long> {
    Collection<TrainingFeedbackEvent> findByIsWatchedFalse();

    void deleteByTrainingFeedbackId(long id);

    Page<TrainingFeedbackEvent> findAll(Pageable pageable);
}

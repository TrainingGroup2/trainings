package com.exadel.repository.events;

import com.exadel.model.entity.events.TrainingEvent;
import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.training.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface TrainingEventRepository extends JpaRepository<TrainingEvent, Long> {
    Collection<TrainingEvent> findByIsWatchedFalse();

    Page<TrainingEvent> findAll(Pageable pageable);
}

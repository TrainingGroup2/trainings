package com.exadel.repository.events;

import com.exadel.model.entity.events.TrainingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface TrainingEventRepository extends JpaRepository<TrainingEvent, Long> {
    Collection<TrainingEvent> findByIsWatchedFalse();
}

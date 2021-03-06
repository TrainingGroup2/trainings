package com.exadel.repository.events;

import com.exadel.model.entity.events.UserFeedbackEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface UserFeedbackEventRepository extends JpaRepository<UserFeedbackEvent, Long> {
    Collection<UserFeedbackEvent> findByIsWatchedFalse();

    Page<UserFeedbackEvent> findAll(Pageable pageable);
}

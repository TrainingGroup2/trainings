package com.exadel.repository;

import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long>{

    @Query(value = "SELECT * FROM user_feedbacks WHERE user_id = ?1 ORDER BY date DESC", nativeQuery = true)
    List<UserFeedback> findByVisitor(long id);

    List<UserFeedback> findByVisitorId(long userId);
}
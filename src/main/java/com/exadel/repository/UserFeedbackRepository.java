package com.exadel.repository;

import com.exadel.model.entity.feedback.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long>{

    @Query(value = "SELECT * FROM user_feedbacks WHERE user_id = ?1 ORDER BY date DESC", nativeQuery = true)
    List<UserFeedback> findByVisitor(long id);


}
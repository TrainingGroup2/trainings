package com.exadel.repository;


import com.exadel.model.entity.TrainingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingFeedbackRepository extends JpaRepository<TrainingFeedback, Long> {
    List<TrainingFeedback> findAll();

    List<TrainingFeedback> findByTrainingId(long trainingId);

    void delete(TrainingFeedback deleted);

    TrainingFeedback save(TrainingFeedback persisted);
}

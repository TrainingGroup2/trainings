package com.exadel.service;

import com.exadel.model.entity.TrainingFeedback;

import java.util.Collection;
import java.util.Optional;

public interface TrainingFeedbackService {

    Optional<TrainingFeedback> getTrainingFeedbackById(long id);

    Collection<TrainingFeedback> getAllTrainingFeedbacks();

    Collection<TrainingFeedback> getTrainingFeedbacksByTrainingId(long trainingId);

    Optional<TrainingFeedback> addTrainingFeedback(TrainingFeedback trainingFeedback);
}

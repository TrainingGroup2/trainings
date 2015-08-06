package com.exadel.service;

import com.exadel.model.entity.ParticipationStatus;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;

import java.util.Collection;
import java.util.List;

public interface TrainingService {

    Training getTrainingById(String id);

    Training getTrainingById(long id);

    Collection<Training> getAllTrainings();

    long addTraining(Training training);

    void updateTraining(Training training);

    ParticipationStatus checkParticipation(String userId, String trainingId);

    void cancelById(String id);

    double addRating(int rating, String trainingId);

    long getTrainerId(long id);

    List<Long> getParticipants(long id);

    List<Training> getTrainingsByTrainingStatus(TrainingStatus status);
}

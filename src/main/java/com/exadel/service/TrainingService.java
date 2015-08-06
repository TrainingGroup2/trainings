package com.exadel.service;

import com.exadel.model.entity.ParticipationStatus;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    Page<Training> getTrainings(Integer pageNumber, Integer size);
}

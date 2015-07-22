package com.exadel.service;

import com.exadel.model.entity.Training;

import java.util.Collection;
import java.util.Optional;

public interface TrainingService {

    Optional<Training> getTrainingById(long id);

    Collection<Training> getAllTrainings();

    Optional<Training> addTraining(Training training);

    void deleteById(long id);
}

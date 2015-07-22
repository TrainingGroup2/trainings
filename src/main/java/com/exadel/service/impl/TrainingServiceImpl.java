package com.exadel.service.impl;

import com.exadel.model.entity.Rating;
import com.exadel.model.entity.Training;
import com.exadel.repository.RatingRepository;
import com.exadel.repository.TrainingRepository;
import com.exadel.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService{
    private final TrainingRepository trainingRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, RatingRepository ratingRepository) {
        this.trainingRepository = trainingRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Optional<Training> getTrainingById(long id) {
        return Optional.ofNullable(trainingRepository.findOne(id));
    }

    @Override
    public Collection<Training> getAllTrainings() {
        Collection<Training> trainings = trainingRepository.findAll(new Sort("name"));
        for (Training training : trainings) {
            double sum = 0;
            int count = 0;
            for (Rating rating : ratingRepository.findByTrainingId(training.getId())) {
                count++;
                sum += rating.getRating();
            }

            training.setRating(sum / count);
        }
        return trainings;
    }

    @Override
    public Optional<Training> addTraining(Training training) {
        return Optional.ofNullable(trainingRepository.saveAndFlush(training));
    }

    @Override
    public void deleteById(long id) {
        trainingRepository.delete(id);
    }
}

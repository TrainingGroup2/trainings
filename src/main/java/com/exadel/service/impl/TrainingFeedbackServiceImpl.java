package com.exadel.service.impl;

import com.exadel.model.entity.TrainingFeedback;
import com.exadel.repository.TrainingFeedbackRepository;
import com.exadel.service.TrainingFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TrainingFeedbackServiceImpl implements TrainingFeedbackService{
    private final TrainingFeedbackRepository trainingFeedbackRepository;

    @Autowired
    public TrainingFeedbackServiceImpl(TrainingFeedbackRepository trainingFeedbackRepository) {
        this.trainingFeedbackRepository = trainingFeedbackRepository;
    }

    @Override
    public Optional<TrainingFeedback> getTrainingFeedbackById(long id) {
        return Optional.ofNullable(trainingFeedbackRepository.findOne(id));
    }

    @Override
    public Collection<TrainingFeedback> getAllTrainingFeedbacks() {
        return trainingFeedbackRepository.findAll();
    }

    @Override
    public Optional<TrainingFeedback> addTrainingFeedback(TrainingFeedback trainingFeedback) {
        System.out.println(trainingFeedback);

        return Optional.ofNullable(trainingFeedbackRepository.saveAndFlush(trainingFeedback));
    }
}

package com.exadel.service.impl;

import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.repository.TrainingFeedbackRepository;
import com.exadel.service.TrainingFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TrainingFeedbackServiceImpl implements TrainingFeedbackService{
    @Autowired
    private TrainingFeedbackRepository trainingFeedbackRepository;

    @Override
    public Optional<TrainingFeedback> getTrainingFeedbackById(long id) {
        return Optional.ofNullable(trainingFeedbackRepository.findOne(id));
    }

    @Override
    public Collection<TrainingFeedback> getAllTrainingFeedbacks() {
        return trainingFeedbackRepository.findAll();
    }

    @Override
    public Collection<TrainingFeedback> getTrainingFeedbacksByTrainingId(long trainingId) {
        return trainingFeedbackRepository.findByTrainingId(trainingId);
    }

    @Override
    public long addTrainingFeedback(TrainingFeedback trainingFeedback) {
        trainingFeedbackRepository.saveAndFlush(trainingFeedback);
        return trainingFeedback.getId();
    }

    @Override
    public void deleteById(long id) {
        trainingFeedbackRepository.delete(id);
    }

}

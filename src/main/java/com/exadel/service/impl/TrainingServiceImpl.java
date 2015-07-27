package com.exadel.service.impl;

import com.exadel.controller.Participation;
import com.exadel.exception.TrainingNotFoundException;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.model.entity.user.User;
import com.exadel.repository.TrainingRepository;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
//@Configurable
@Transactional(rollbackFor = {TrainingNotFoundException.class})
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Training getTrainingById(String id) {
        try {
            long trainingId = Long.parseLong(id);
            return getTrainingById(trainingId);
        } catch (NumberFormatException ex) {
            throw new TrainingNotFoundException(id);
        }
    }

    public Training getTrainingById(long id) {
        Training training = trainingRepository.findOne(id);
        if (training != null) {
            return training;
        }
        else {
            throw new TrainingNotFoundException(String.valueOf(id));
        }
    }

    @Override
    public Collection<Training> getAllTrainings() {
        Collection<Training> trainings = trainingRepository.findAll(new Sort("name"));

        for (Training training : trainings) {
            if (training.getValuerCount() == 0)
                training.setRating(0);
            else
                training.setRating((double)(training.getRatingSum()) / ((double) training.getValuerCount()));
        }
        return trainings;
    }

    @Override
    public Optional<Training> addTraining(Training training) {
        return Optional.ofNullable(trainingRepository.saveAndFlush(training));
    }

    @Override
    public void updateTraining(Training training) {
        if (trainingRepository.exists(training.getId())) {
            trainingRepository.save(training);
        }
        else
            throw new TrainingNotFoundException(String.valueOf(training.getId()));
    }

    @Override
    public Participation checkParticipation(String userId, String trainingId) {
        long id = userService.getUserById(userId).getId();
        Training training = getTrainingById(trainingId);
        List<User> participants = training.getParticipants();

        for (int i = 0; i < participants.size(); ++i) {
            if (participants.get(i).getId() == id) {
                if (i < training.getMembersCountMax())
                    return Participation.MEMBER;
                else
                    return Participation.RESERVE;
            }
        }

        return Participation.NONE;
    }

    @Override
    public void cancelById(String id) {
        long trainingId = Long.parseLong(id);
        Training training = trainingRepository.getOne(trainingId);
        if (training != null) {
            training.setStatus(TrainingStatus.CANCELLED);
            trainingRepository.save(training);
        }
        else
            throw new TrainingNotFoundException(id);
    }

    @Override
    public double addRating(int grade, String trainingId) {
        Training training = getTrainingById(trainingId);
        training.setValuerCount(training.getValuerCount() + 1);
        training.setRatingSum(training.getRatingSum() + grade);
        return (double)(training.getRatingSum() / training.getValuerCount());
    }
}

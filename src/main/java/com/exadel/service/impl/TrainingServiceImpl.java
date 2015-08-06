package com.exadel.service.impl;

import com.exadel.exception.TrainingNotFoundException;
import com.exadel.model.entity.ParticipationStatus;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.model.entity.user.User;
import com.exadel.repository.TrainingRepository;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(rollbackFor = {TrainingNotFoundException.class})
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private UserService userService;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

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
        } else {
            throw new TrainingNotFoundException(String.valueOf(id));
        }

    }

    public long getTrainerId(long id) {
        long trainerId = this.jdbcTemplate.queryForObject(
                "select trainer_id from trainings where id = ?",
                Long.class, id);
        return trainerId;
    }

    @Override
    public List<Long> getParticipants(long id) {
        List<Long> participants = this.jdbcTemplate.queryForList(
                "select user_id FROM training_users where training_id=?", new Object[]{id},
                Long.class);
        return participants;
    }

    @Override
    public List<Training> getTrainingsByTrainingStatus(TrainingStatus status) {
        return trainingRepository.findByStatus(status);
    }

    @Override
    public Collection<Training> getAllTrainings() {
        Collection<Training> trainings = trainingRepository.findAll(new Sort("name"));

        for (Training training : trainings) {
            if (training.getValuerCount() == 0)
                training.setRating(0);
            else
                training.setRating((double) (training.getRatingSum()) / ((double) training.getValuerCount()));
        }
        return trainings;
    }

    @Override
    public long addTraining(Training training) {
        trainingRepository.saveAndFlush(training);
        return training.getId();
    }

    @Override
    public void updateTraining(Training training) {
        if (trainingRepository.exists(training.getId())) {
            trainingRepository.save(training);
        } else
            throw new TrainingNotFoundException(String.valueOf(training.getId()));
    }

    @Override
    public ParticipationStatus checkParticipation(String userId, String trainingId) {
        long id = userService.getUserById(userId).getId();
        Training training = getTrainingById(trainingId);
        List<User> participants = training.getParticipants();

        for (int i = 0; i < participants.size(); ++i) {
            if (participants.get(i).getId() == id) {
                if (i < training.getMembersCountMax())
                    return ParticipationStatus.MEMBER;
                else
                    return ParticipationStatus.RESERVE;
            }
        }

        return ParticipationStatus.NONE;
    }

    @Override
    public void cancelById(String id) {
        long trainingId = Long.parseLong(id);
        Training training = trainingRepository.getOne(trainingId);
        if (training != null) {
            training.setStatus(TrainingStatus.CANCELLED);
            trainingRepository.save(training);
        } else
            throw new TrainingNotFoundException(id);
    }

    @Override
    public double addRating(int grade, String trainingId) {
        Training training = getTrainingById(trainingId);
        training.setValuerCount(training.getValuerCount() + 1);
        training.setRatingSum(training.getRatingSum() + grade);
        return (double) (training.getRatingSum() / training.getValuerCount());
    }
}

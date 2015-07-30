package com.exadel.service.impl;

import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.repository.UserFeedbackRepository;
import com.exadel.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService{
    private final UserFeedbackRepository userFeedbackRepository;

    @Autowired
    public UserFeedbackServiceImpl(UserFeedbackRepository userFeedbackRepository) {
        this.userFeedbackRepository = userFeedbackRepository;
    }

    @Override
    public Optional<UserFeedback> getFeedbackById(long id) {
        return Optional.ofNullable(userFeedbackRepository.findOne(id));
    }

    @Override
    public List<UserFeedback> getFeedbacksByVisitor(long id) {
        return userFeedbackRepository.findByVisitor(id);
    }

    @Override
    public UserFeedback addFeedback(UserFeedback feedback) {
        return userFeedbackRepository.saveAndFlush(feedback);
    }
}

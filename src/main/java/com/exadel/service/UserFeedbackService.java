package com.exadel.service;

import com.exadel.model.entity.events.UserFeedbackEvent;
import com.exadel.model.entity.feedback.UserFeedback;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserFeedbackService {
    Optional<UserFeedback> getFeedbackById(long id);

    List<UserFeedback> getFeedbacksOnVisitor(long id);

    UserFeedback addFeedback(UserFeedback feedback);

}
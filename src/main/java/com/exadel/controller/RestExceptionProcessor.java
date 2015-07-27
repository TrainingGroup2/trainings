package com.exadel.controller;

import com.exadel.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
public class RestExceptionProcessor {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(TrainingNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo trainingNotFound(HttpServletRequest req, TrainingNotFoundException ex) {

        String errorMessage = localizeErrorMessage("error.no.training.id");
        errorMessage += ex.getTrainingId();
        String errorUrl = req.getRequestURL().toString();
        return new ErrorInfo(errorUrl, errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo userNotFound(HttpServletRequest req, UserNotFoundException ex) {

        String errorMessage = localizeErrorMessage("error.no.user.id");
        errorMessage += ex.getUserId();
        String errorUrl = req.getRequestURL().toString();
        return new ErrorInfo(errorUrl, errorMessage);
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo trainerNotFound(HttpServletRequest req, TrainerNotFoundException ex) {

        String errorMessage = localizeErrorMessage("error.no.user.id");
        errorMessage += ex.getUserId();
        String errorUrl = req.getRequestURL().toString();
        return new ErrorInfo(errorUrl, errorMessage);
    }

    @ExceptionHandler(UserHasNotVisitingTrainingsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo userHasNotVisitingTrainings(HttpServletRequest req, UserHasNotVisitingTrainingsException ex) {

        String errorMessage = localizeErrorMessage("error.no.user.visiting");
        errorMessage += ex.getUserId();
        String errorUrl = req.getRequestURL().toString();
        return new ErrorInfo(errorUrl, errorMessage);
    }

    @ExceptionHandler(UserHasNotMentoringTrainingsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo userHasNotMentoringTrainings(HttpServletRequest req, UserHasNotMentoringTrainingsException ex) {

        String errorMessage = localizeErrorMessage("error.no.user.mentoring");
        errorMessage += ex.getUserId();
        String errorUrl = req.getRequestURL().toString();
        return new ErrorInfo(errorUrl, errorMessage);
    }

    public String localizeErrorMessage(String errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        //Locale locale = new Locale("ru","RU");
        return messageSource.getMessage(errorCode, null, locale);
    }
}

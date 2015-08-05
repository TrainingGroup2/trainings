package com.exadel.service.impl;

import com.exadel.model.entity.ParticipationStatus;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailMessages {
    @Autowired
    private MessageSource messageSource;

    public static final String domain = "http://localhost:8080/";

    public String modifyTraining(Training training){
        Object[] arr = {training.getName(), domain + "training/info/?trainingId=" + training.getId()};
        return messageSource.getMessage("emailNotification.training.modify", arr, null);
    }

    public String deleteTraining(Training training){
        Object[] arr = {training.getName()};
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("email.Notification.training.delete", arr, null);
    }

    public String modifyEntry(Entry entry){
        Object[] arr = {entry.getTraining().getName(), domain + "training/entries/?trainingId=" + entry.getTraining().getId()};
        return messageSource.getMessage("emailNotification.training.modify", arr, null);
    }

    public String deleteEntry(Entry entry){
        Object[] arr = {entry.getBeginTime().toString(), entry.getTraining().getName(), domain + "training/entries/?trainingId=" + entry.getTraining().getId()};
        return messageSource.getMessage("emailNotification.training.deleteEntry", arr, null);
    }

    public String deleteFeedback(TrainingFeedback feedback){
        Object[] arr = {feedback.getFeedbacker().getName(), feedback.getTraining().getName()};
        return messageSource.getMessage("emailNotification.training.feedback.delete", arr, null);
    }

    public String askFeedback(Training training, User user){
        Object[] arr = {training.getTrainer().getName(), user.getName() + " " + user.getSurname(), training.getName()};
        return messageSource.getMessage("emailNotification.askFeedback", arr, null);
    }

    public String register(User user, Entry nextEntry, ParticipationStatus status){
        if(status == ParticipationStatus.MEMBER){
            Object[] arr = {user.getName(), nextEntry.getTraining().getName(), nextEntry.getBeginTime(), nextEntry.getPlace()};
            return messageSource.getMessage("emailNotification.register.member", arr, null);
        }

        else{
            Object[] arr = {user.getName(), nextEntry.getTraining().getName()};
            return messageSource.getMessage("emailNotification.register.reserve", arr, null);
        }
    }

    public String becomeMember(User user, Entry nextEntry){   //TODO: link to confirm participation
        Object[] arr = {user.getName(), nextEntry.getTraining().getName(), nextEntry.getBeginTime(), nextEntry.getPlace(),
                "domain + link to confirm participation", domain + "training/entries/?trainingId=" + nextEntry.getTraining().getId()};
        return messageSource.getMessage("emailNotification.register.becomeMember", arr, null);
    }

    //Scheduled notifications
    public String beforeDay(Entry entry){
        Object[] arr = {entry.getTraining().getName(), entry.getBeginTime().toString(),
                domain + "training/entries/?trainingId=" + entry.getTraining().getId()};
        return messageSource.getMessage("emailNotification.training.beforeDay", arr, null);
    }

    public String beforeHour(Entry entry){
        Object[] arr = {entry.getTraining().getName(), entry.getBeginTime().toString()};
        return messageSource.getMessage("emailNotification.training.beforeHour", arr, null);
    }

    //Event notifications for admin
    public String deleteEntryToAdmin(Entry entry){
        Object[] arr = {entry.getBeginTime(), entry.getTraining().getName()};
        return messageSource.getMessage("eventNotification.training.entry.delete", arr, null);
    }

    public String deleteTrainingToAdmin(Training training){
        Object[] arr = {training.getName()};
        return messageSource.getMessage("eventNotification.training.delete", arr, null);
    }

    public String lessThanHalf(Training training){
        Object[] arr = {training.getName()};
        return messageSource.getMessage("eventNotification.training.lessThanHalf", arr, null);
    }
}

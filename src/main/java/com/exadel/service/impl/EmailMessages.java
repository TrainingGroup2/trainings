package com.exadel.service.impl;

import com.exadel.model.entity.ParticipationStatus;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.text.SimpleDateFormat;

@Service
public class EmailMessages  {
    @Autowired
    private MessageSource messageSource;
    private static final String domain = "http://localhost:9000/#/";

    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat fullDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final String key = "Bar12345Bar12345";
    private static final Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    private static Cipher cipher;

    static{
        try{
            cipher = Cipher.getInstance("AES");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static String doCrypto(int cipherMode, String str){
        String encrypted = null;
        try {
            if (cipherMode==Cipher.ENCRYPT_MODE) {
                cipher.init(cipherMode, aesKey);
                encrypted = new String(cipher.doFinal(str.getBytes()));
                encrypted = URLEncoder.encode(encrypted, "UTF-8");
            }
            else {
                str = URLDecoder.decode(str, "UTF-8");
                cipher.init(cipherMode, aesKey);
                encrypted = new String(cipher.doFinal(str.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    public String modifyTraining(Training training) {
        Object[] arr = {
                training.getName(),
                domain + "training/" + training.getId()
        };
        return messageSource.getMessage("emailNotification.training.modify", arr, null);
    }

    public String deleteTraining(Training training) {
        Object[] arr = {training.getName()};
        return messageSource.getMessage("email.Notification.training.delete", arr, null);
    }

    public String modifyEntry(Entry entry) {
        Object[] arr = {
                entry.getTraining().getName(),
                domain + "training/" + entry.getTraining().getId()
        };
        return messageSource.getMessage("emailNotification.training.modify", arr, null);
    }

    public String deleteEntry(Entry entry) {
        Object[] arr = {
                fullDate.format(entry.getBeginTime()),
                entry.getTraining().getName(),
                domain + "training/" + entry.getTraining().getId()
        };
        return messageSource.getMessage("emailNotification.training.deleteEntry", arr, null);
    }

    public String deleteFeedback(TrainingFeedback feedback) {
        Object[] arr = {
                feedback.getFeedbacker().getName(),
                feedback.getTraining().getName()
        };
        return messageSource.getMessage("emailNotification.training.feedback.delete", arr, null);
    }

    public String askFeedback(Training training, User user) {
        Object[] arr = {
                training.getTrainer().getName(),
                user.getName() + " " + user.getSurname(),
                training.getName()
        };
        return messageSource.getMessage("emailNotification.askFeedback", arr, null);
    }

    public String register(User user, Entry nextEntry, ParticipationStatus status) {
        String trainingId = doCrypto(Cipher.ENCRYPT_MODE, Long.toString(nextEntry.getTraining().getId()));
        String userId = doCrypto(Cipher.ENCRYPT_MODE, Long.toString(user.getId()));

        if (status == ParticipationStatus.MEMBER) {
            Object[] arr = {
                    user.getName(),
                    nextEntry.getTraining().getName(),
                    fullDate.format(nextEntry.getBeginTime()),
                    nextEntry.getPlace(),
                    domain + "training/cancel_participation/" + userId + "/" + trainingId
            };
            return messageSource.getMessage("emailNotification.register.member", arr, null);
        } else {
            Object[] arr = {
                    user.getName(),
                    nextEntry.getTraining().getName(),
                    domain + "training/cancel_participation/?userId=" + userId + "/&trainingId=" + trainingId
            };
            return messageSource.getMessage("emailNotification.register.reserve", arr, null);
        }
    }

    public String becomeMember(User user, Entry nextEntry) {
        String trainingId = doCrypto(Cipher.ENCRYPT_MODE, Long.toString(nextEntry.getTraining().getId()));
        String userId = doCrypto(Cipher.ENCRYPT_MODE, Long.toString(user.getId()));
        Object[] arr = {
                user.getName(),
                nextEntry.getTraining().getName(),
                fullDate.format(nextEntry.getBeginTime()),
                nextEntry.getPlace(),
                domain + "training/confirm_participation/" + userId + "/" + trainingId,
                domain + "training/" + nextEntry.getTraining().getId()
        };
        return messageSource.getMessage("emailNotification.register.becomeMember", arr, null);
    }

    public String newExternalTrainer(ExternalTrainer trainer, String password) {
        Object[] arr = {
                trainer.getName(),
                domain + "userTrainings",
                password
        };
        return messageSource.getMessage("emailNotification.newExternalTrainer", arr, null);
    }

    //Scheduled notifications
    public String beforeDay(User user, Entry entry) {
        String trainingId = doCrypto(Cipher.ENCRYPT_MODE, Long.toString(entry.getTraining().getId()));
        String userId = doCrypto(Cipher.ENCRYPT_MODE, Long.toString(user.getId()));
        Object[] arr = {
                user.getName(),
                entry.getTraining().getName(),
                time.format(entry.getBeginTime()),
                domain + "training/" + entry.getTraining().getId(),
                domain + "training/cancel_participation/" + userId + "/" + trainingId
        };
        return messageSource.getMessage("emailNotification.training.beforeDay", arr, null);
    }

    public String beforeHour(Entry entry) {
        Object[] arr = {entry.getTraining().getName(), time.format(entry.getBeginTime())};
        return messageSource.getMessage("emailNotification.training.beforeHour", arr, null);
    }

    //Event notifications for admin
    public String deleteEntryToAdmin(Entry entry) {
        Object[] arr = {
                fullDate.format(entry.getBeginTime()),
                entry.getTraining().getName(),
                domain + "training/" + entry.getTraining().getId()
        };
        return messageSource.getMessage("eventNotification.training.entry.delete", arr, null);
    }

    public String deleteTrainingToAdmin(Training training) {
        Object[] arr = {training.getName(), domain + "training/" + training.getId()};
        return messageSource.getMessage("eventNotification.training.delete", arr, null);
    }

    public String lessThanHalf(Training training) {
        Object[] arr = {training.getName(), domain + "training/" + training.getId()};
        return messageSource.getMessage("eventNotification.training.lessThanHalf", arr, null);
    }

    public String newTrainingToAdmin(Training training) {
        Object[] arr = {
                training.getTrainer().getName(),
                training.getName(),
                domain + "training/" + training.getId()
        };
        return messageSource.getMessage("eventNotification.training.new", arr, null);
    }
}
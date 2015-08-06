package com.exadel.service.impl;

import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Reserve;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.training.TrainingStatus;
import com.exadel.model.entity.user.User;
import com.exadel.model.entity.user.UserRole;
import com.exadel.service.EntryService;
import com.exadel.service.ReserveService;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class ScheduleService {
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    private EntryService entryService;
    @Autowired
    private EmailMessages emailMessages;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private UserService userService;
    @Autowired
    ReserveService reserveService;

    @Scheduled(fixedRate = 3600*1000)
    public void beforeDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.HOUR, 23);
        Date before = calendar.getTime();
        calendar.add(calendar.HOUR, 1);
        Date after = calendar.getTime();
        List<Entry> entries = entryService.getBetweenDates(before, after);

        for(Entry entry: entries) {
            Training training = entry.getTraining();
            if (training.isRepeated()==false && training.getStatus()== TrainingStatus.APPROVED) {
                for (User user: training.getParticipants()){
                    smtpMailSender.send(user.getEmail(), "Training", emailMessages.beforeDay(user, entry));
                }
            }
            if (training.getStatus()== TrainingStatus.APPROVED &&
                    training.getParticipants().size() < training.getMembersCountMax()/2) {
                    smtpMailSender.sendToUsers(userService.getUsersByRole(UserRole.ADMIN), "Training", emailMessages.lessThanHalf(training));
            }
        }
    }

    @Scheduled(fixedRate = 15*60*1000)
    public void beforeHour(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.MINUTE, 45);
        Date before = calendar.getTime();
        calendar.add(calendar.MINUTE, 15);
        Date after = calendar.getTime();
        List<Entry> entries = entryService.getBetweenDates(before, after);

        for(Entry entry: entries) {
            if (entry.getTraining().getStatus()== TrainingStatus.APPROVED) {
                smtpMailSender.sendToUsers(entry.getTraining().getParticipants(), "Training", emailMessages.beforeHour(entry));
            }
        }
    }

    @Scheduled(fixedRate = 15*60*1000)
    public void before3Hours(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.MINUTE, 165);
        Date before = calendar.getTime();
        calendar.add(calendar.MINUTE, 15);
        Date after = calendar.getTime();
        List<Entry> entries = entryService.getBetweenDates(before, after);

        for(Entry entry: entries) {
            if (entry.getTraining().getStatus()== TrainingStatus.APPROVED) {
                Training training = entry.getTraining();
                if (training.getParticipants().size() < (double)training.getMembersCountMax()*2/3) {
                    trainingService.cancelById(String.valueOf(training.getId()));
                    smtpMailSender.sendToUsers(training.getParticipants(), "Training", emailMessages.deleteTraining(training));
                }
                else { //challenge
                    List<Reserve> reserves = reserveService.getAllReservesByTrainingId(training.getId());
                    if(training.getParticipants().size() < (double)training.getMembersCountMax() && reserves != null) {
                        for (Reserve reserve: reserves){
                            smtpMailSender.send(reserve.getReservist().getEmail(), "Training", emailMessages.becomeMember(reserve.getReservist(), entry));
                        }
                    }
                }
            }
        }
    }

    @Scheduled(fixedRate = 24*3600*1000)
    public void completeTrainings(){
        List<Training> trainings = trainingService.getTrainingsByTrainingStatus(TrainingStatus.APPROVED);
        Date today = new Date();

        for(Training training: trainings){
            if (entryService.findNextEntryByTrainingId(today, training.getId())==null){
                training.setStatus(TrainingStatus.COMPLETED);
                trainingService.updateTraining(training);
            }
        }
    }
}

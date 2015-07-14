package com.exadel.controller;

import com.exadel.model.entity.Training;
import com.exadel.model.entity.User;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainings/training")
public class TrainingPageController {

    Training getTrainingById(String id) {
        for(Training training: TrainingsController.trainings){
            if (training.getId().equals((id))){
                return training;
            }
        }
        return null;
    }

    @RequestMapping(value="participants", method = RequestMethod.GET)
    public List<User> getParticipants(@RequestParam String id) {
        List<User> participants = new ArrayList<>();
        participants.addAll(UserController.users);  //later it will be request from db for participants of this training
        return participants;
    }

    @RequestMapping(value="attachments", method = RequestMethod.GET)
    public List<Pair<String, String>> getAttachments(@RequestParam String id) {
        List<Pair<String, String>> attachments = new ArrayList<>();
        attachments.add(new Pair("lection 1", "http://..lections/lec1.ppt"));
        attachments.add(new Pair("lection 2", "http://..lections/lec2.ppt"));
        //later it will be request from db for attchmentss of this training
        return attachments;
    }
}

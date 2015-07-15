package com.exadel.controller;

import com.exadel.model.constants.TrainingStatus;
import com.exadel.model.entity.Employee;
import com.exadel.model.entity.ExternalVisitor;
import com.exadel.model.entity.Training;
import com.exadel.model.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/trainings")
public class TrainingsController {
    public static List<Training> trainings = new ArrayList<Training>();
    private static Logger logger = Logger.getLogger(TrainingsController.class.getName());
    static {
        User trainer = new Employee("1255", "The", "Best", "Trainer", "+37529656599", "marina@mail.ru", null, null);
        trainings.add(new Training("1", "Marina", "204", new Date(), new Date(), trainer, "developers", 4.5, 32, 2, TrainingStatus.approved, null, null));
        trainings.add(new Training("2", "Petya", "205", new Date(), new Date(), trainer, "developers", 2.7, 27, 11, TrainingStatus.cancelled, null, null));
        trainings.add(new Training("3", "Ivan", "206", new Date(), new Date(), trainer, "developers", 4.7, 102, 12, TrainingStatus.drafted, null, null));
    }
    public static Training getTrainingById(String id) {
        for(Training training: TrainingsController.trainings){
            if (training.getId().equals((id))){
                return training;
            }
        }
        return null;
    }
    @RequestMapping(method=RequestMethod.GET)
    public List<Training> getTrainings(){
        return trainings;
    }

    @RequestMapping(value="/newTraining", method = RequestMethod.POST)
    public Training createTraining(@RequestBody Training training) {
        trainings.add(training);
        return training;
    }
    @RequestMapping(value="/training", method = RequestMethod.DELETE)
    public void deleteTraining(@RequestParam String id) {
        Training trainingToDelete = TrainingsController.getTrainingById(id);
        if (trainingToDelete != null) {
            trainings.remove(trainingToDelete);
        }
    }
}
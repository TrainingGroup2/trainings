package com.exadel.controller;

import com.exadel.model.entity.Training;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingsController {
    public static List<Training> trainings = new ArrayList<Training>();

    static {
        trainings.add(new Training("1", "Vasya", "204", new Date(), new Date(), "1", "1st best trainer", "developers", 4.5, 32, 10, 5));
        trainings.add(new Training("2", "Petya", "205", new Date(), new Date(), "2'", "2nd best trainer", "developers", 2.7, 27, 11, 6));
        trainings.add(new Training("3", "Ivan", "206", new Date(), new Date(), "3", "3rd best trainer", "developers", 4.7, 102, 12, 7));
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
}
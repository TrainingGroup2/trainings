package com.exadel.controller;

import com.exadel.model.entity.Training;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping("/trainings")
public class TrainingsController {
    static List<Training> trainings = new ArrayList<Training>();

    static {
        trainings.add(new Training("1", "Vasya", "204", new Date(), new Date(), new Date(), "1st best trainer", "developers", 4.5, 32));
        trainings.add(new Training("1", "Petya", "205", new Date(), new Date(), new Date(), "2nd best trainer", "developers", 2.7, 27));
        trainings.add(new Training("1", "Ivan", "206", new Date(), new Date(), new Date(), "3rd best trainer", "developers", 4.7, 102));
    }

    @RequestMapping(value="/trainings", method=RequestMethod.GET)
    public List<Training> getTrainings(){
        return trainings;
    }

    /*@RequestMapping(value="/trainings/newTraining", method = RequestMethod.POST)
    public Training createTraining(@RequestBody @Valid Training training){
        System.out.println(training);
        return training;
    }*/
}
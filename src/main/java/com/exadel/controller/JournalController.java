package com.exadel.controller;

import com.exadel.model.entity.Journal;
import com.exadel.model.entity.Training;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainings/journal")
public class JournalController {
    static List<Training> trainings = new ArrayList<Training>();

    static {
        trainings.add(new Training("1", "Vasya", "204", new Date(), new Date(), new Date(), "1st best trainer", "developers", 4.5, 32, 10, 5));
        trainings.add(new Training("1", "Petya", "205", new Date(), new Date(), new Date(), "2nd best trainer", "developers", 2.7, 27, 11, 6));
        trainings.add(new Training("1", "Ivan", "206", new Date(), new Date(), new Date(), "3rd best trainer", "developers", 4.7, 102, 12, 7));
    }


    public List getJournal(@RequestParam(value="trainingId") String id) {
        return new Journal(Integer.parseInt(id)).getAttendance();
    }
}

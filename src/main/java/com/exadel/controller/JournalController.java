package com.exadel.controller;

import com.exadel.model.entity.Journal;
import com.exadel.model.entity.Training;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainings/journal")
public class JournalController {
    static List<Training> trainings = new ArrayList<Training>();

    public List getJournal(@RequestParam(value="trainingId") String id) {
        return new Journal(Integer.parseInt(id)).getAttendance();
    }
}

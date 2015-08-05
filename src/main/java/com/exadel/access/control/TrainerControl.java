package com.exadel.access.control;

import com.exadel.dto.AttachmentDTO;
import com.exadel.dto.EntryDTO;
import com.exadel.dto.TrainingDTO;
import com.exadel.model.entity.training.Entry;
import com.exadel.model.entity.training.Training;
import com.exadel.service.EntryService;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "trainerControlBean")
public class TrainerControl {

    public final UserService userService;
    public final TrainingService trainingService;
    public final EntryService entryService;

    @Autowired
    public TrainerControl(UserService userService, TrainingService trainingService, EntryService entryService) {
        this.userService = userService;
        this.trainingService = trainingService;
        this.entryService = entryService;
    }

    public boolean isCoach(long id) {
        if (userService.getCurrentId()==trainingService.getTrainerId(id))
            return true;
        else
            return false;
    }

    public boolean isCoach(TrainingDTO trainingDTO) {
        long id = trainingDTO.getTrainerId();
        if (userService.getCurrentId() == trainingService.getTrainerId(id))
            return true;
        else
            return false;
    }

    public boolean isCoach(AttachmentDTO attachmentDTO) {
        long trainingId = attachmentDTO.getTrainingId();
        if (userService.getCurrentId() == trainingService.getTrainerId(trainingId))
            return true;
        else
            return false;
    }

    public boolean isTrainer(String entryId) {
       Entry entry= entryService.getEntryById(entryId);
       Training training= entry.getTraining();
        long id = training.getId();
        return isCoach(id);
    }

    public boolean isTrainer(EntryDTO entryDTO) {
        long id = entryDTO.getTrainingId();
        return isCoach(id);
    }
    public boolean isTrainer(List<EntryDTO> entryDTOs) {

        long id = entryDTOs.get(0).getTrainingId();
        if (userService.getCurrentId() == trainingService.getTrainerId(id))
            return true;
        else
            return false;
    }
}

package com.exadel.access.control;

import com.exadel.dto.AttachmentDTO;
import com.exadel.dto.EntryDTO;
import com.exadel.dto.TrainingDTO;
import com.exadel.model.entity.training.Training;
import com.exadel.service.TrainingService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "trainerControlBean")
public class TrainerControl {

    public final UserService userService;
    public final TrainingService trainingService;

    @Autowired
    public TrainerControl(UserService userService, TrainingService trainingService) {
        this.userService = userService;
        this.trainingService = trainingService;
    }

    public boolean isCoach(long id) {
        if (userService.getCurrentId()==trainingService.getTrainerId(id))
            return true;
        else
            return false;
    }

    public boolean isCoach(TrainingDTO trainingDTO) {
        long id = trainingDTO.getTrainer().getId();
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

    public boolean isTrainer(List<EntryDTO> entryDTOs) {

        long id = entryDTOs.get(0).getTrainingId();
        if (userService.getCurrentId() == trainingService.getTrainerId(id))
            return true;
        else
            return false;
    }
}

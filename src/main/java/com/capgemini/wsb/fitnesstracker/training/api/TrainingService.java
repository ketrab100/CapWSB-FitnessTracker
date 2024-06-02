package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<Training> getTrainings();

    List<Training> getTrainingsByUserId(long userId);

    List<Training> getTrainingsByActivity(ActivityType activityType);

    List<Training> getTrainingsByEndDate(Date date);

    Training createTraining(Training training);

    Training updateTraining(Long id, Training updateTraining);

}

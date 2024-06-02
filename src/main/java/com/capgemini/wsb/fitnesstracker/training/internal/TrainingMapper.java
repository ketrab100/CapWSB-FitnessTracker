package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    @Autowired
    private UserTrainingMapper userTrainingMapper;

    public TrainingDto toDto(Training training) {
        return new TrainingDto(training.getId(), training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed(), userTrainingMapper.toDto(training.getUser()));
    }

    public Training toEntity(TrainingDto trainingDto) {
        return new Training(userTrainingMapper.toEntity(trainingDto.user()), trainingDto.startTime(), trainingDto.endTime(), trainingDto.activityType(), trainingDto.distance(), trainingDto.averageSpeed());
    }
}

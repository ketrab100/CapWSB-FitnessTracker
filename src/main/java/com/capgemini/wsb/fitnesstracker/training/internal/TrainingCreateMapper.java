package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingCreateMapper {

    @Autowired
    private UserService userService;

    public Training toEntity(TrainingCreateDto trainingDto) {
        var user = userService.getUser(trainingDto.userId());
        return user.map(value -> new Training(value, trainingDto.startTime(), trainingDto.endTime(), trainingDto.activityType(), trainingDto.distance(), trainingDto.averageSpeed())).orElse(null);
    }
}

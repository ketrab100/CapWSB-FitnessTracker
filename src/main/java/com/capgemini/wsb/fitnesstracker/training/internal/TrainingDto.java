package com.capgemini.wsb.fitnesstracker.training.internal;

import jakarta.annotation.Nullable;

import java.util.Date;

public record TrainingDto(
        @Nullable
        Long id,

        Date startTime,

        Date endTime,

        ActivityType activityType,

        double distance,

        double averageSpeed,

        UserDto user
) {
}

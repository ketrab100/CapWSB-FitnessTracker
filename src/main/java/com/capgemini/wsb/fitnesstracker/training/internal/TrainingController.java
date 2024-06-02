package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;
    private final TrainingCreateMapper trainingCreateMapperMapper;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getTrainings() {
        var trainings = trainingService.getTrainings().stream().map(trainingMapper::toDto).toList();
        return ResponseEntity.ok().body(trainings);
    }

    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getTrainingsFinished(@PathVariable(required = true, name = "afterTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        var trainings = trainingService.getTrainingsByEndDate(afterTime).stream().map(trainingMapper::toDto).toList();
        return ResponseEntity.ok().body(trainings);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUserId(@PathVariable(required = true, name = "userId") Long userId) {
        var trainings = trainingService.getTrainingsByUserId(userId).stream().map(trainingMapper::toDto).toList();
        return ResponseEntity.ok().body(trainings);
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getTrainingsByActivityTime(@RequestParam("activityType") ActivityType activityType) {
        var trainings = trainingService.getTrainingsByActivity(activityType).stream().map(trainingMapper::toDto).toList();
        return ResponseEntity.ok().body(trainings);
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingCreateDto trainingDto) {
        var training = trainingCreateMapperMapper.toEntity(trainingDto);
        training = trainingService.createTraining(training);

        return new ResponseEntity(training, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long id, @RequestBody TrainingCreateDto trainingCreateDto) {
        var training = trainingCreateMapperMapper.toEntity(trainingCreateDto);
        training = trainingService.updateTraining(id, training);
        var trainingResponse = trainingMapper.toDto(training);

        return ResponseEntity.ok().body(trainingResponse);
    }
}

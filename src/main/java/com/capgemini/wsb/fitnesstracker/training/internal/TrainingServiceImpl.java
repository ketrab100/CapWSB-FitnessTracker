package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;

    /**
     * Gets all trainings from DB
     * @return list of trainings
     */
    @Override
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Gets training by id
     * @param trainingId id of the training to be searched
     * @return optional training
     */
    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    /**
     * Gets trainings by user id
     * @param userId
     * @return list of trainings
     */
    @Override
    public List<Training> getTrainingsByUserId(long userId) {
        return trainingRepository.findAll().stream().filter(x -> x.getUser().getId() == userId).collect(Collectors.toList());
    }

    /**
     * Gets trainings by activity type
     * @param activityType
     * @return list of trainings
     */
    @Override
    public List<Training> getTrainingsByActivity(ActivityType activityType) {
        return trainingRepository.findAll().stream().filter(x -> x.getActivityType() == activityType).collect(Collectors.toList());
    }

    /**
     * Gets trainings by end date, all trainings which end date is after date
     * @param date
     * @return list of trainings
     */
    @Override
    public List<Training> getTrainingsByEndDate(Date date) {
        return trainingRepository.findAll().stream().filter(x -> x.getEndTime().after(date)).collect(Collectors.toList());
    }

    /**
     * Creates new training
     * @param training
     * @return created training
     */
    @Override
    public Training createTraining(Training training) {
        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

    /**
     * Updates training
     * @param id
     * @param updateTraining
     * @return updated training
     */
    @Override
    public Training updateTraining(Long id, Training updateTraining) {
        Optional<Training> optionalTraining = trainingRepository.findById(id);
        if (optionalTraining.isPresent()) {
            Training training = optionalTraining.get();
            if (updateTraining.getStartTime() != null) {
                training.setStartTime(updateTraining.getStartTime());
            }
            if (updateTraining.getEndTime() != null) {
                training.setEndTime(updateTraining.getEndTime());
            }
            if (updateTraining.getActivityType() != null) {
                training.setActivityType(updateTraining.getActivityType());
            }
            training.setDistance(updateTraining.getDistance());

            training.setAverageSpeed(updateTraining.getAverageSpeed());

            training.setUser(updateTraining.getUser());

            return trainingRepository.save(training);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}

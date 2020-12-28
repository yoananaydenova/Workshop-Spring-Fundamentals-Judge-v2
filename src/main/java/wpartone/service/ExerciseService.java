package wpartone.service;

import wpartone.model.service.ExerciseServiceModel;

import java.util.List;

public interface ExerciseService {
    void addExercise(ExerciseServiceModel exerciseServiceModel);

    List<String> findAllExerciseNames();

    ExerciseServiceModel findByName(String exercise);
}

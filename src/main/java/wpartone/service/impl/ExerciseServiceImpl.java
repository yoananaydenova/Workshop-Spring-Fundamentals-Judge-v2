package wpartone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wpartone.model.entity.Exercise;
import wpartone.model.service.ExerciseServiceModel;
import wpartone.repository.ExerciseRepository;
import wpartone.service.ExerciseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addExercise(ExerciseServiceModel exerciseServiceModel) {

        this.exerciseRepository.saveAndFlush(this.modelMapper.map(exerciseServiceModel, Exercise.class));
    }

    @Override
    public List<String> findAllExerciseNames() {
        return this.exerciseRepository
                .findAll()
                .stream()
                .map(Exercise::getName)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseServiceModel findByName(String exercise) {
        return this.exerciseRepository
                .findByName(exercise)
                .map(exer -> this.modelMapper.map(exer, ExerciseServiceModel.class))
                .orElse(null);

    }
}

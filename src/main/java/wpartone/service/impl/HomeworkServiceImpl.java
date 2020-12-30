package wpartone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wpartone.model.entity.Homework;
import wpartone.model.service.HomeworkServiceModel;
import wpartone.repository.HomeworkRepository;
import wpartone.service.HomeworkService;

import java.util.Comparator;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, ModelMapper modelMapper) {
        this.homeworkRepository = homeworkRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(HomeworkServiceModel homeworkServiceModel) {
        this.homeworkRepository.saveAndFlush(this.modelMapper.map(homeworkServiceModel, Homework.class));
    }

    @Override
    public HomeworkServiceModel findOneToCheck() {
        return this.homeworkRepository
                .findAll()
                .stream()
                .min(Comparator.comparingInt(a -> a.getComments().size()))
                .map(homework -> this.modelMapper.map(homework, HomeworkServiceModel.class))
                .orElse(null);
    }

    @Override
    public HomeworkServiceModel findById(String homeworkId) {
        return this.homeworkRepository
                .findById(homeworkId)
                .map(homework -> this.modelMapper.map(homework, HomeworkServiceModel.class))
                .orElse(null);
    }
}

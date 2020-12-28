package wpartone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wpartone.model.entity.Homework;
import wpartone.model.service.HomeworkServiceModel;
import wpartone.repository.HomeworkRepository;
import wpartone.service.HomeworkService;

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
}

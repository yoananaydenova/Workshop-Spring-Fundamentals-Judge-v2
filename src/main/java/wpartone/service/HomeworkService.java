package wpartone.service;

import wpartone.model.service.HomeworkServiceModel;

public interface HomeworkService {
    void add(HomeworkServiceModel homeworkServiceModel);

    HomeworkServiceModel findOneToCheck();

    HomeworkServiceModel findById(String homeworkId);
}

package wpartone.service;

import wpartone.model.service.CommentServiceModel;

import java.util.HashMap;

public interface CommentService {
    void add(CommentServiceModel commentServiceModel);

    Double getAverageScore();

    HashMap<Integer, Integer> getScoreMap();
}

package wpartone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wpartone.model.entity.Comment;
import wpartone.model.service.CommentServiceModel;
import wpartone.repository.CommentRepository;
import wpartone.service.CommentService;

import java.util.HashMap;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void add(CommentServiceModel commentServiceModel) {
        this.commentRepository.saveAndFlush(this.modelMapper.map(commentServiceModel, Comment.class));
    }

    @Override
    public Double getAverageScore() {
        return this.commentRepository
                .findAll()
                .stream()
                .mapToDouble(Comment::getScore)
                .average()
                .orElse(0D);
    }

    @Override
    public HashMap<Integer, Integer> getScoreMap() {
        HashMap<Integer, Integer> map = new HashMap<>();

        this.commentRepository
                .findAll()
                .forEach(comment -> {
                    int score = comment.getScore();

                    map.put(score, map.containsKey(score) ? map.get(score) + 1 : 1);
                });
        return map;
    }
}

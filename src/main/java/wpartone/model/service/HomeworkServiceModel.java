package wpartone.model.service;

import wpartone.model.entity.Exercise;
import wpartone.model.entity.User;

import java.time.LocalDateTime;

public class HomeworkServiceModel extends BaseServiceModel{

    private LocalDateTime addedOn;
    private String gitAddress;
    private UserServiceModel author;
    private ExerciseServiceModel exercise;

    public HomeworkServiceModel() {
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }

    public ExerciseServiceModel getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseServiceModel exercise) {
        this.exercise = exercise;
    }
}

package wpartone.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class HomeworkAddBindingModel {

    private String exercise;
    private String gitAddress;

    public HomeworkAddBindingModel() {
    }

    @Length(min=3, message = "Exercise name length must be more than 2 characters")
    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    @Pattern(regexp = "https:\\/\\/github\\.com\\/.+\\/.+", message = "Enter git address following this pattern")
    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }
}

package wpartone.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wpartone.service.CommentService;
import wpartone.service.ExerciseService;
import wpartone.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final UserService userService;
    private final CommentService commentService;
    private final ExerciseService exerciseService;

    public HomeController(UserService userService, CommentService commentService, ExerciseService exerciseService) {
        this.userService = userService;
        this.commentService = commentService;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        model.addAttribute("totalUserCount", this.userService.findTotalUsersCount());
        model.addAttribute("average", this.commentService.getAverageScore());
        model.addAttribute("scoreMap", this.commentService.getScoreMap());
        model.addAttribute("activeExercise", this.exerciseService.findAllExerciseNames());
        return "home";
    }
}

package wpartone.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wpartone.model.binding.CommentAddBindingModel;
import wpartone.model.binding.HomeworkAddBindingModel;
import wpartone.model.service.CommentServiceModel;
import wpartone.model.service.ExerciseServiceModel;
import wpartone.model.service.HomeworkServiceModel;
import wpartone.model.service.UserServiceModel;
import wpartone.service.CommentService;
import wpartone.service.ExerciseService;
import wpartone.service.HomeworkService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    private final ExerciseService exerciseService;
    private final HomeworkService homeworkService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public HomeworkController(ExerciseService exerciseService, HomeworkService homeworkService, CommentService commentService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.homeworkService = homeworkService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("homeworkAddBindingModel")) {
            model.addAttribute("homeworkAddBindingModel", new HomeworkAddBindingModel());
            model.addAttribute("isLate", false);
        }
        model.addAttribute("allExerciseNames", this.exerciseService.findAllExerciseNames());

        return "homework-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("homeworkAddBindingModel") HomeworkAddBindingModel homeworkAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        ExerciseServiceModel exerciseServiceModel = this.exerciseService.findByName(homeworkAddBindingModel.getExercise());

        boolean isLate = exerciseServiceModel.getDueDate().isBefore(LocalDateTime.now());

        if (bindingResult.hasErrors() || isLate) {
            redirectAttributes.addFlashAttribute("homeworkAddBindingModel", homeworkAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeworkAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isLate", isLate);

            return "redirect:add";
        }

        // TODO That must be in HomeworkService
        HomeworkServiceModel homeworkServiceModel = this.modelMapper
                .map(homeworkAddBindingModel, HomeworkServiceModel.class);
        homeworkServiceModel.setAddedOn(LocalDateTime.now());
        homeworkServiceModel.setExercise(exerciseServiceModel);
        homeworkServiceModel.setAuthor((UserServiceModel) httpSession.getAttribute("user"));
        this.homeworkService.add(homeworkServiceModel);

        return "redirect:/";
    }


    @GetMapping("/check")
    public String check(Model model) {

        if(!model.containsAttribute("commentAddBindingModel")){
            model.addAttribute("commentAddBindingModel", new CommentAddBindingModel());
        }

        HomeworkServiceModel homeworkServiceModel = this.homeworkService
                .findOneToCheck();

        model.addAttribute("homework",homeworkServiceModel );

        return "homework-check";
    }

    @PostMapping("/check")
    public String checkConfirm(@Valid @ModelAttribute("commentAddBindingModel")CommentAddBindingModel commentAddBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession httpSession){

        // TODO check if homework is to same user, if it is -> change it

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel", bindingResult);

            return "redirect:check";
        }

        // TODO That must be in CommentService

        CommentServiceModel commentServiceModel = this.modelMapper.map(commentAddBindingModel, CommentServiceModel.class);
        commentServiceModel.setHomework(this.homeworkService.findById(commentAddBindingModel.getHomeworkId()));
        commentServiceModel.setAuthor((UserServiceModel) httpSession.getAttribute("user"));

        this.commentService.add(commentServiceModel);
        return "redirect:/";

    }
}

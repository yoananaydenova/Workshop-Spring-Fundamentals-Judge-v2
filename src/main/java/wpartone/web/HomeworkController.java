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
import wpartone.model.binding.HomeworkAddBindingModel;
import wpartone.model.service.ExerciseServiceModel;
import wpartone.model.service.HomeworkServiceModel;
import wpartone.model.service.UserServiceModel;
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
    private final ModelMapper modelMapper;

    public HomeworkController(ExerciseService exerciseService, HomeworkService homeworkService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.homeworkService = homeworkService;
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

        HomeworkServiceModel homeworkServiceModel = this.modelMapper
                .map(homeworkAddBindingModel, HomeworkServiceModel.class);

        homeworkServiceModel.setAddedOn(LocalDateTime.now());
        homeworkServiceModel.setExercise(exerciseServiceModel);
        homeworkServiceModel.setAuthor((UserServiceModel) httpSession.getAttribute("user"));

        this.homeworkService.add(homeworkServiceModel);
        return "redirect:/";
    }
}

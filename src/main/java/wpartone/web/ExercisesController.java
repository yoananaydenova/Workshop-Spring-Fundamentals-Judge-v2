package wpartone.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wpartone.model.binding.ExerciseBindingAddModel;
import wpartone.model.service.ExerciseServiceModel;
import wpartone.service.ExerciseService;

import javax.validation.Valid;

@Controller
@RequestMapping("/exercises")
public class ExercisesController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;

    @Autowired
    public ExercisesController(ExerciseService exerciseService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(@Valid @ModelAttribute("exerciseBindingAddModel")ExerciseBindingAddModel exerciseBindingAddModel,
                      BindingResult bindingResult) {
        return "exercise-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("exerciseBindingAddModel")ExerciseBindingAddModel exerciseBindingAddModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("exerciseBindingAddModel", exerciseBindingAddModel);
            return "redirect:/exercises/add";
        }else{
            // TODO if daye in binding model is before LocalDate.now()
           this.exerciseService.addExercise(this.modelMapper.map(exerciseBindingAddModel, ExerciseServiceModel.class));
            return "redirect:/";
        }

    }
}

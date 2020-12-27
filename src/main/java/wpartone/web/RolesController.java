package wpartone.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wpartone.model.binding.ExerciseBindingAddModel;
import wpartone.model.binding.RoleAddBindingModel;
import wpartone.service.RoleService;
import wpartone.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RolesController {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RolesController(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("exerciseBindingAddModel") ExerciseBindingAddModel exerciseBindingAddModel,
                            BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.addObject("usernames", this.userService.findAllUsernames());
        modelAndView.setViewName("role-add");
        return modelAndView;
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("roleAddBindingModel")RoleAddBindingModel roleAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("roleAddBindingModel", roleAddBindingModel);
            return "redirect:/roles/add";
        }else{
          // TODO validate
            this.userService.addRoleToUser(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());

            return "redirect:/";
        }

    }

}

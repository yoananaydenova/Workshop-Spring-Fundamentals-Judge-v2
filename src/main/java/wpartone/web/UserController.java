package wpartone.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wpartone.model.binding.UserAddBindingModel;
import wpartone.model.binding.UserLoginBindingModel;
import wpartone.model.service.UserServiceModel;
import wpartone.model.view.UserProfileViewModel;
import wpartone.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel,
                              BindingResult bindingResult,
                              ModelAndView modelAndView) {

        modelAndView.addObject("userLoginBindingModel", userLoginBindingModel);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@Valid @ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel,
                                     BindingResult bindingResult,
                                     ModelAndView modelAndView,
                                     HttpSession httpSession,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            modelAndView.setViewName("redirect:/users/login");

        } else {
            UserServiceModel userServiceModel = this.userService.findByUsername(userLoginBindingModel.getUsername());

            if (userServiceModel == null ||
                    !userServiceModel.getPassword().equals(userLoginBindingModel.getPassword())) {

                redirectAttributes.addFlashAttribute("notFound", true);
                redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
                modelAndView.setViewName("redirect:/users/login");
            } else {
                httpSession.setAttribute("user", userServiceModel);
                httpSession.setAttribute("id", userServiceModel.getId());
                httpSession.setAttribute("role", userServiceModel.getRole().getName());
                modelAndView.setViewName("redirect:/");
            }
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public String register(@Valid @ModelAttribute("userAddBindingModel") UserAddBindingModel userAddBindingModel,
                           BindingResult bindingResult) {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userAddBindingModel") UserAddBindingModel userAddBindingModel,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView,
                                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userAddBindingModel", userAddBindingModel);
            modelAndView.setViewName("redirect:/users/register");
        } else {

            UserServiceModel userServiceModel = this.userService
                    .registerUser(this.modelMapper.map(userAddBindingModel, UserServiceModel.class));

            modelAndView.setViewName("redirect:/users/login");
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){

        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile (Model model, @RequestParam("id")String id){

        model.addAttribute("user", this.modelMapper
        .map(this.userService.findById(id), UserProfileViewModel.class));
        return "profile";
    }

}

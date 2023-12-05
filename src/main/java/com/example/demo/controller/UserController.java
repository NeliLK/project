package com.example.demo.controller;

import com.example.demo.model.dto.UserRegistrationBindingModel;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView loginPost(){
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/users/login-error")
    public String onFailure(
            @ModelAttribute("username") String username,
            Model model) {

        model.addAttribute("username", username);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }


    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationBindingModel") UserRegistrationBindingModel userRegistrationBindingMode) {
       ModelAndView modelAndView = new ModelAndView("register");

       modelAndView.addObject("userRegistrationBindingModel", new UserRegistrationBindingModel());

       return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(
            @ModelAttribute("userRegistrationBindingModel")
            @Valid UserRegistrationBindingModel userRegistrationBindingModel,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");

            modelAndView.addObject("userRegistrationBindingModel", userRegistrationBindingModel);
            return modelAndView;
        }

        boolean hasSuccessfulRegistration = userService.register(userRegistrationBindingModel);

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }
        return new ModelAndView("redirect:/login");
    }

}

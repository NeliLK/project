package com.example.demo.controller;

import com.example.demo.model.dto.UserLoginBindingModel;
import com.example.demo.model.dto.UserRegistrationBindingModel;
import com.example.demo.service.LoggedUser;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final UserService userService;
    private final LoggedUser loggedUser;

    public UserController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel) {
        if (loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(
            @ModelAttribute("userLoginBindingModel") @Valid UserLoginBindingModel userLoginBindingModel,
            BindingResult bindingResult
    ) {
        if (loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("login");
        }

        boolean hasSuccessfulRegistration = userService.login(userLoginBindingModel);

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("hasLoginError", true);
            return modelAndView;
        }
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationBindingModel")
                                 UserRegistrationBindingModel userRegistrationBindingModel) {
        if (loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("register");
    }


    @PostMapping("/register")
    public ModelAndView register(
            @ModelAttribute("userRegistrationBindingModel")
            @Valid UserRegistrationBindingModel userRegistrationBindingModel,
            BindingResult bindingResult) {

        if (loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        boolean hasSuccessfulRegistration = userService.register(userRegistrationBindingModel);

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/logout")
    public ModelAndView logout() {
        if (!loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }
        this.userService.logout();

        return new ModelAndView("redirect:/");
    }
}

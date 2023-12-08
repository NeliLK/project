package com.example.demo.controller;

import com.example.demo.model.dto.PetAddBindingModel;
import com.example.demo.model.dto.PetViewModel;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.service.PetService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("petAddBindingModel") PetAddBindingModel petAddBindingModel) {

        return new ModelAndView("pet-add");
    }

    @PostMapping("/add")
    public ModelAndView addPet(@ModelAttribute("petAddBindingModel") @Valid PetAddBindingModel petAddBindingModel,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("pet-add");
        }

        Pet addedPet = petService.add(petAddBindingModel);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/all")
    public ModelAndView listPets() {
        ModelAndView modelAndView = new ModelAndView("pet-all");

        User loggedInUser = userService.getLoggedInUser();

        boolean isAdmin = loggedInUser.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(UserRoleEnum.ADMIN));

        List<PetViewModel> pets;
        if (isAdmin) {
            pets = petService.getAllPets();
        } else {
            pets = petService.getHomeViewData();

        }
        modelAndView.addObject("pets", pets);

        return modelAndView;
    }
}

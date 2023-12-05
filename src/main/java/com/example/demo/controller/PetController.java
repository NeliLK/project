package com.example.demo.controller;

import com.example.demo.model.dto.PetAddBindingModel;
import com.example.demo.model.dto.PetViewModel;
import com.example.demo.model.entity.Pet;
import com.example.demo.service.PetService;
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

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("petAddBindingModel") PetAddBindingModel petAddBindingModel) {

        return new ModelAndView("pet-add");
    }

    @PostMapping("/add")
    public ModelAndView addPet(@ModelAttribute("petAddBindingModel") @Valid PetAddBindingModel petAddBindingModel,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return  new ModelAndView ("pet-add");
        }

        Pet addedPet = petService.add(petAddBindingModel);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/all")
    public ModelAndView listPets() {
        ModelAndView modelAndView = new ModelAndView("pet-all"); // Thymeleaf template name

        List <PetViewModel> pets = petService.getHomeViewData();

        modelAndView.addObject("pets", pets);

        return modelAndView;
    }
}

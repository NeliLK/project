package com.example.demo.controller;

import com.example.demo.model.dto.VetAddBindingModel;
import com.example.demo.service.VetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vets")
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("vetAddBindingModel") VetAddBindingModel vetAddBindingModel) {

        return new ModelAndView("vet-add");
    }

    @PostMapping("/add")
    public ModelAndView addPet(@ModelAttribute("vetAddBindingModel") @Valid VetAddBindingModel vetAddBindingModel,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("vet-add");
        }

        vetService.add(vetAddBindingModel);

        return new ModelAndView("redirect:/");
    }
}

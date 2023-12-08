package com.example.demo.controller;

import com.example.demo.model.dto.VetAddBindingModel;
import com.example.demo.service.VetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView addVet(@ModelAttribute("vetAddBindingModel") @Valid VetAddBindingModel vetAddBindingModel,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("vet-add");
        }

        vetService.add(vetAddBindingModel);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/all")
    public ModelAndView getAllVetNames() {
        ModelAndView modelAndView = new ModelAndView("vet-all");
        modelAndView.addObject("vetNames", vetService.getAllVetNames());
        return modelAndView;
    }
}

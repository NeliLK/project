package com.example.demo.controller;

import com.example.demo.model.dto.MedicalRecordAddBindingModel;
import com.example.demo.model.dto.MedicalRecordViewModel;
import com.example.demo.service.MedicalRecordService;
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
@RequestMapping("/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;
    private final PetService petService;

    public MedicalRecordController(MedicalRecordService medicalRecordService, PetService petService) {
        this.medicalRecordService = medicalRecordService;
        this.petService = petService;
    }

    @GetMapping("/add")
    public ModelAndView showAddForm() {
        ModelAndView modelAndView = new ModelAndView("medical-record-add");
        modelAndView.addObject("medicalRecordAddBindingModel", new MedicalRecordAddBindingModel());

        List<String> allPets = petService.getAllPetNames();
        modelAndView.addObject("availablePets", allPets);

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addMedicalRecord(@ModelAttribute("medicalRecordAddBindingModel") @Valid MedicalRecordAddBindingModel medicalRecordAddBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("medical-record-add");
        }

        medicalRecordService.add(medicalRecordAddBindingModel);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/all")
    public ModelAndView listMedicalRecords() {
        ModelAndView modelAndView = new ModelAndView("medical-records"); // Thymeleaf template name

        List <MedicalRecordViewModel> medicalRecords = medicalRecordService.getMedicalRecordsViewData();

        modelAndView.addObject("medicalRecord", medicalRecords);

        return modelAndView;
    }
}



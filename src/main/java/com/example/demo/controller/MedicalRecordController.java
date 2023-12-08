package com.example.demo.controller;

import com.example.demo.model.dto.MedicalRecordAddBindingModel;
import com.example.demo.model.dto.MedicalRecordViewModel;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.service.MedicalRecordService;
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
@RequestMapping("/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;
    private final PetService petService;
    private final UserService userService;

    public MedicalRecordController(MedicalRecordService medicalRecordService, PetService petService, UserService userService) {
        this.medicalRecordService = medicalRecordService;
        this.petService = petService;
        this.userService = userService;
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
        ModelAndView modelAndView = new ModelAndView("medical-records");

        User loggedInUser = userService.getLoggedInUser();

        boolean isAdmin = loggedInUser.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(UserRoleEnum.ADMIN));

        List<MedicalRecordViewModel> medicalRecords;
        if (isAdmin) {
            medicalRecords = medicalRecordService.getAllMedicalRecords();
        } else {
            medicalRecords = medicalRecordService.getMedicalRecordsViewData();
        }

        modelAndView.addObject("medicalRecord", medicalRecords);

        return modelAndView;
    }
}



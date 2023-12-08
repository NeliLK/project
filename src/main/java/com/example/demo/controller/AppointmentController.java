package com.example.demo.controller;

import com.example.demo.model.dto.AppointmentAddBindingModel;
import com.example.demo.model.dto.AppointmentViewModel;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.service.*;
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
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final PetService petService;
    private final VetService vetService;
    private final UserService userService;


    public AppointmentController(AppointmentService appointmentService, PetService petService, VetService vetService, UserService userService) {
        this.appointmentService = appointmentService;
        this.petService = petService;
        this.vetService = vetService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("appointmentAddBindingModel") AppointmentAddBindingModel appointmentAddBindingModel) {
        ModelAndView modelAndView = new ModelAndView("appointment-add");

        List<String> petNames = petService.getPetNamesByUser();
        List<String> vetNames = vetService.getAllVetNames();

//        if (vetNames.isEmpty()) {
//            return (ModelAndView) Collections.emptyList();

        modelAndView.addObject("petNames", petNames);
        modelAndView.addObject("vetNames", vetNames);
        modelAndView.addObject("appointmentModel", new AppointmentAddBindingModel());

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView createAppointment(@ModelAttribute("appointmentAddBindingModel") @Valid AppointmentAddBindingModel appointmentAddBindingModel,
                                          BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<String> petNames = petService.getPetNamesByUser();
        List<String> vetNames = vetService.getAllVetNames();

        modelAndView.addObject("petNames", petNames);
        modelAndView.addObject("vetNames", vetNames);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("appointment-add");
        } else {
            try {
                appointmentService.addAppointment(appointmentAddBindingModel);
                modelAndView.setViewName("redirect:/");
            } catch (IllegalArgumentException e) {
                modelAndView.setViewName("appointment-add");
                modelAndView.addObject("noPetsMessage", e.getMessage());
            } catch (RuntimeException e) {
                modelAndView.setViewName("appointment-add");
                modelAndView.addObject("errorMessage", e.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView listAppointments() {
        ModelAndView modelAndView = new ModelAndView("appointment-all");

        User loggedInUser = userService.getLoggedInUser();

        boolean isAdmin = loggedInUser.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(UserRoleEnum.ADMIN));

        List<AppointmentViewModel> appointments;
        if (isAdmin) {

            appointments = appointmentService.getAllAppointments();
        } else {

            appointments = appointmentService.getAppointmentsForUser();
        }

        modelAndView.addObject("appointments", appointments);

        return modelAndView;
    }
}

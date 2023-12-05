package com.example.demo.service.impl;

import com.example.demo.model.dto.AppointmentAddBindingModel;
import com.example.demo.model.dto.AppointmentViewModel;
import com.example.demo.model.entity.Appointment;
import com.example.demo.model.entity.Pet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.VetRepository;
import com.example.demo.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PetRepository petRepository, VetRepository vetRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addAppointment(AppointmentAddBindingModel appointmentAddBindingModel) {
        String username = getCurrentUsername();
        Pet pet = petRepository.findByPetNameAndUser_Username(appointmentAddBindingModel.getPetName(), username);

        if (pet == null) {
            throw new IllegalArgumentException("Pet not found for the user.");
        }

        Appointment appointment = modelMapper.map(appointmentAddBindingModel, Appointment.class);

//        if (hasExistingAppointmentForPet(appointment) || hasExistingAppointmentForVet(appointment)) {
//            throw new RuntimeException("Pet or Vet already has an appointment at the specified date and time.");
//        }

        appointment.setPet(pet);
        appointment.setVet(vetRepository.findByVetName(appointmentAddBindingModel.getVetName()));
        appointment.setAppointmentDateTime(appointmentAddBindingModel.getAppointmentDateTime());

        appointmentRepository.save(appointment);
    }

    public List<AppointmentViewModel> getAppointmentsForUser() {
        String username = getCurrentUsername();

        List<Pet> userPets = petRepository.findByUser_Username(username);
        List<Appointment> appointments = new ArrayList<>();

        for (Pet pet : userPets) {
            appointments.addAll(appointmentRepository.findByPetId(pet.getId()));
        }

        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentViewModel.class))
                .collect(Collectors.toList());
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

//    private boolean hasExistingAppointmentForPet(Appointment newAppointment) {
//        List<Appointment> petAppointments = appointmentRepository.findByPetAndAppointmentDateTime(
//                newAppointment.getPet(), newAppointment.getAppointmentDateTime());
//
//        return petAppointments.stream().anyMatch(existingAppointment -> existingAppointment.overlapsWith(newAppointment));
//    }
//
}

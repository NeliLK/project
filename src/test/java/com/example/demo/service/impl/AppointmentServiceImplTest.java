package com.example.demo.service.impl;

import com.example.demo.model.dto.AppointmentAddBindingModel;
import com.example.demo.model.dto.AppointmentViewModel;
import com.example.demo.model.entity.Appointment;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.Vet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "testPassword");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testAddAppointmentPetNotFound() {
        AppointmentAddBindingModel addBindingModel = new AppointmentAddBindingModel();
        addBindingModel.setPetName("Buddy");
        addBindingModel.setVetName("Dr. Smith");
        addBindingModel.setAppointmentDateTime(LocalDateTime.now());

        when(petRepository.findByPetNameAndUser_Username(anyString(), anyString())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment(addBindingModel);
        });
    }

    @Test
    void testGetAppointmentsForUser() {
        Pet pet1 = new Pet();
        pet1.setId(1L);
        Pet pet2 = new Pet();
        pet2.setId(2L);

        when(petRepository.findByUser_Username(anyString())).thenReturn(Arrays.asList(pet1, pet2));

        List<Appointment> mockAppointments = Arrays.asList(
                new Appointment(),
                new Appointment()
        );

        when(appointmentRepository.findByPetId(1L)).thenReturn(mockAppointments.subList(0, 1));
        when(appointmentRepository.findByPetId(2L)).thenReturn(mockAppointments.subList(1, 2));

        when(modelMapper.map(any(Appointment.class), eq(AppointmentViewModel.class)))
                .thenReturn(new AppointmentViewModel());

        List<AppointmentViewModel> appointments = appointmentService.getAppointmentsForUser();

        assertEquals(2, appointments.size());
    }

}

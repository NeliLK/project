package com.example.demo.service.impl;

import com.example.demo.model.dto.VetAddBindingModel;
import com.example.demo.model.entity.Vet;
import com.example.demo.repository.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VetServiceImplTest {
    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetServiceImpl vetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddVet() {
        VetAddBindingModel vetAddBindingModel = new VetAddBindingModel();
        vetAddBindingModel.setVetName("Dr. Smith");
        vetAddBindingModel.setSpecialization("Surgery");

        vetService.add(vetAddBindingModel);

        verify(vetRepository, times(1)).save(argThat(vet ->
                vet.getVetName().equals("Dr. Smith") && vet.getSpecialization().equals("Surgery")));
    }

    @Test
    void testGetAllVetNames() {
        List<Vet> mockVets = Arrays.asList(
                new Vet("Dr. Smith", "Surgery"),
                new Vet("Dr. Johnson", "Dentistry")
        );
        when(vetRepository.findAll()).thenReturn(mockVets);

        List<String> vetNames = vetService.getAllVetNames();

        assertEquals(Arrays.asList("Dr. Smith", "Dr. Johnson"), vetNames);
    }

    @Test
    void testGetAllVetNamesWhenEmpty() {
        // Arrange
        when(vetRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> vetService.getAllVetNames());
    }
}

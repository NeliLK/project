package com.example.demo.service.impl;

import com.example.demo.model.dto.PetAddBindingModel;
import com.example.demo.model.dto.PetViewModel;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @Mock
    private PetRepository mockPetRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPetByNameAndUserId() {
        // Arrange
        String petName = "Fluffy";
        String username = "testUser";
        when(mockPetRepository.findByPetNameAndUser_Username(petName, username)).thenReturn(new Pet());

        // Act
        Pet result = petService.getPetByNameAndUserId(petName, username);

        // Assert
        assertNotNull(result);
        verify(mockPetRepository, times(1)).findByPetNameAndUser_Username(petName, username);
    }

    @Test
    void testGetAllPetNames() {
        // Arrange
        when(mockPetRepository.findAll()).thenReturn(Arrays.asList(new Pet(), new Pet()));

        // Act
        List<String> result = petService.getAllPetNames();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mockPetRepository, times(1)).findAll();
    }

}

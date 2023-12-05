package com.example.demo.service.impl;

import com.example.demo.model.dto.MedicalRecordAddBindingModel;
import com.example.demo.model.dto.MedicalRecordViewModel;
import com.example.demo.model.entity.MedicalRecord;
import com.example.demo.model.entity.Pet;
import com.example.demo.repository.MedicalRecordRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Test
    void testAddMedicalRecord() {
        // Arrange
        MedicalRecordAddBindingModel bindingModel = new MedicalRecordAddBindingModel();
        bindingModel.setPet("TestPet");
        bindingModel.setDiagnostic("TestDiagnostic");
        bindingModel.setTreatment("TestTreatment");

        Pet testPet = new Pet();
        testPet.setPetName("TestPet");

        when(petRepository.findByPetName("TestPet")).thenReturn(Optional.of(testPet));
        when(modelMapper.map(bindingModel, MedicalRecord.class)).thenReturn(new MedicalRecord());

        // Act
        medicalRecordService.add(bindingModel);

        // Assert
        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
    }
}



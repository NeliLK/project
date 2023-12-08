package com.example.demo.service.impl;

import com.example.demo.model.dto.MedicalRecordAddBindingModel;
import com.example.demo.model.dto.MedicalRecordViewModel;
import com.example.demo.model.entity.MedicalRecord;
import com.example.demo.model.entity.Pet;
import com.example.demo.repository.MedicalRecordRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MedicalRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, PetRepository petRepository, ModelMapper modelMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(MedicalRecordAddBindingModel medicalRecordAddBindingModel) {
        String petName = medicalRecordAddBindingModel.getPet();

        Pet pet = petRepository.findByPetName(petName)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

//        if (pet.getMedicalRecord() != null) {
//            throw new IllegalStateException("Pet already has a medical record");
//        }

        MedicalRecord medicalRecord = modelMapper.map(medicalRecordAddBindingModel, MedicalRecord.class);
        medicalRecord.setPet(pet);

        medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public List<MedicalRecordViewModel> getMedicalRecordsViewData() {
        String username = getCurrentUsername();

        List<Pet> userPets = petRepository.findByUser_Username(username);

        List<MedicalRecordViewModel> medicalRecordViewModels = new ArrayList<>();
        for (Pet pet : userPets) {
            if (pet.getMedicalRecord() != null) {
                MedicalRecord medicalRecord = pet.getMedicalRecord();
                MedicalRecordViewModel viewModel = modelMapper.map(medicalRecord, MedicalRecordViewModel.class);
                medicalRecordViewModels.add(viewModel);
            }
        }
        return medicalRecordViewModels;
    }

    @Override
    public List<MedicalRecordViewModel> getAllMedicalRecords() {
        List<MedicalRecord> allMedicalRecords = medicalRecordRepository.findAll();

        return allMedicalRecords.stream()
                .map(record -> modelMapper.map(record, MedicalRecordViewModel.class))
                .collect(Collectors.toList());
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    //    @Override
//    public void updateMedicalRecord(Long id, MedicalRecordAddBindingModel medicalRecordAddBindingModel) {
//        MedicalRecord existingMedicalRecord = medicalRecordRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("MedicalRecord not found with id: " + id));
//
//        modelMapper.map(medicalRecordAddBindingModel, existingMedicalRecord);
//
//        medicalRecordRepository.save(existingMedicalRecord);
//    }
//
//    @Override
//    public void deleteMedicalRecord(Long id) {
//        medicalRecordRepository.deleteById(id);
//    }

}

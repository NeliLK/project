package com.example.demo.service;

import com.example.demo.model.dto.MedicalRecordAddBindingModel;
import com.example.demo.model.dto.MedicalRecordViewModel;

import java.util.List;

public interface MedicalRecordService {
    void add(MedicalRecordAddBindingModel medicalRecordAddBindingModel);

//    void updateMedicalRecord(Long id, MedicalRecordAddBindingModel medicalRecordAddBindingModel);
//
//    void deleteMedicalRecord(Long id);

    List<MedicalRecordViewModel> getMedicalRecordsViewData();

    List<MedicalRecordViewModel> getAllMedicalRecords();
}

package com.example.demo.repository;

import com.example.demo.model.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

//    Optional<MedicalRecord> findById(Long aLong);
}

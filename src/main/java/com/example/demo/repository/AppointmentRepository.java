package com.example.demo.repository;

import com.example.demo.model.entity.Appointment;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long id);

    List<Appointment> findByPetAndAppointmentDateTime(Pet pet, LocalDateTime appointmentDateTime);

    List<Appointment> findByVetAndAppointmentDateTime(Vet vet, LocalDateTime appointmentDateTime);
}

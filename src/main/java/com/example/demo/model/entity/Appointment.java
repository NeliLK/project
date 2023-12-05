package com.example.demo.model.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Vet vet;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    public Appointment() {
    }

    public Appointment(Pet pet, Vet vet, LocalDateTime appointmentDateTime) {
        this.pet = pet;
        this.vet = vet;
        this.appointmentDateTime = appointmentDateTime;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDate) {
        this.appointmentDateTime = appointmentDate;
    }

    public boolean overlapsWith(Appointment other) {
        return this.appointmentDateTime.isEqual(other.appointmentDateTime)
                || this.appointmentDateTime.plusMinutes(30).isAfter(other.appointmentDateTime)
                && other.appointmentDateTime.plusMinutes(30).isAfter(this.appointmentDateTime);
    }
}


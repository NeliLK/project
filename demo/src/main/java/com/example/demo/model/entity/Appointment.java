package com.example.demo.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Vet vet;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    public Appointment() {
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}

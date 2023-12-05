package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "vets")
public class Vet extends BaseEntity {

    @Column(name = "vet_name", nullable = false)
    private String vetName;

    @Column(nullable = false)
    private String specialization;

    public Vet() {
    }

    public Vet(String vetName, String specialization) {
        this.vetName = vetName;
        this.specialization = specialization;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String firstName) {
        this.vetName = firstName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

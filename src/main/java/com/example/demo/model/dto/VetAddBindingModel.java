package com.example.demo.model.dto;

import org.hibernate.validator.constraints.Length;

public class VetAddBindingModel {
    @Length(min = 3, max = 20, message = "Vet name is required")
    private String vetName;

    @Length(min = 3, max = 20, message = "Specialization is required")
    private String specialization ;

    public VetAddBindingModel() {
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

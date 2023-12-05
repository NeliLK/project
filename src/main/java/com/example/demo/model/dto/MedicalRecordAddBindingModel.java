package com.example.demo.model.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class MedicalRecordAddBindingModel {

    @NotNull(message = "Pet name is required")
    private String petName;

    @NotNull(message = "Pet name is required")
    private String diagnostic;

    @NotNull(message = "Treatment description is required")
    @Length(min = 3)
    private String treatment;

    public MedicalRecordAddBindingModel() {
    }

    public String getPet() {
        return petName;
    }

    public void setPet(String pet) {
        this.petName = pet;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}

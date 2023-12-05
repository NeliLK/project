package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PetAddBindingModel {
    @NotBlank(message = "Pet name is required")
    private String petName;

    @NotBlank(message = "Specie is required")
    private String specie ;

    @NotNull(message = "Age is required")
    private Integer age;

    public PetAddBindingModel() {
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

package com.example.demo.service;

import com.example.demo.model.dto.PetAddBindingModel;
import com.example.demo.model.dto.PetViewModel;
import com.example.demo.model.entity.Pet;

import java.util.List;

public interface PetService {
    Pet add(PetAddBindingModel petAddBindingModel);

    List<String> getPetNamesByUser();

    Pet getPetByNameAndUserId(String petName, String username);

    List<String> getAllPetNames();

    List <PetViewModel> getHomeViewData();

    List<PetViewModel> getAllPets();
}

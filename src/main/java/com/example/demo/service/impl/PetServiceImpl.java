package com.example.demo.service.impl;

import com.example.demo.model.dto.PetAddBindingModel;
import com.example.demo.model.dto.PetViewModel;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public Pet add( PetAddBindingModel petAddBindingModel) {
        String username = getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));


        Pet pet = new Pet();
        pet.setPetName(petAddBindingModel.getPetName());
        pet.setSpecie(petAddBindingModel.getSpecie());
        pet.setAge(petAddBindingModel.getAge());
        pet.setUser(user);

        return petRepository.save(pet);
    }

    @Override
    public List<String> getPetNamesByUser() {
        String username = getCurrentUsername();
        List<Pet> userPets = petRepository.findByUser_Username(username);

        if (userPets.isEmpty()) {

            return Collections.emptyList();
        }
        return userPets.stream().map(Pet::getPetName).collect(Collectors.toList());
    }

    @Override
    public Pet getPetByNameAndUserId(String petName, String username) {
        return petRepository.findByPetNameAndUser_Username(petName, username);
    }

    @Override
    public List<String> getAllPetNames() {
        List<Pet> allPets = petRepository.findAll();
        return allPets.stream().map(Pet::getPetName).collect(Collectors.toList());
    }

    @Override
    public List <PetViewModel>  getHomeViewData() {
        String username = getCurrentUsername();

        List<Pet> userPets = petRepository.findByUser_Username(username);

        List<PetViewModel> petViewModels = userPets.stream()
                .map(pet -> modelMapper.map(pet, PetViewModel.class))
                .collect(Collectors.toList());

        return petViewModels;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("Authentication or its name is null");
        }
        return authentication.getName();
    }
}


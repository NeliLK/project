package com.example.demo.service.impl;

import com.example.demo.model.dto.VetAddBindingModel;
import com.example.demo.model.entity.Vet;
import com.example.demo.repository.VetRepository;
import com.example.demo.service.VetService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VetServiceImpl implements VetService {
    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public void add(VetAddBindingModel vetAddBindingModel) {
        Vet vet = new Vet();

        vet.setVetName(vetAddBindingModel.getVetName());
        vet.setSpecialization(vetAddBindingModel.getSpecialization());

        vetRepository.save(vet);
    }

    @Override
    public List<String> getAllVetNames() {
        List<Vet> allVets = vetRepository.findAll();

        if (allVets.isEmpty()) {
            throw new RuntimeException("There are no vets available.");
        }

        return allVets.stream().map(Vet::getVetName).collect(Collectors.toList());
    }
}


package com.example.demo.service;

import com.example.demo.model.dto.VetAddBindingModel;

import java.util.List;

public interface VetService {
    void add(VetAddBindingModel vetAddBindingModel);

    List<String> getAllVetNames();

}

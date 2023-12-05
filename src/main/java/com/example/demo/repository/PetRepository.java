package com.example.demo.repository;

import com.example.demo.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUserId(Long aLong);

    List<Pet> findByUser_Username(String username);

    Pet findByPetNameAndUserId(String petName, Long userId);

    Pet findByPetNameAndUser_Username(String petName, String username);

    Optional<Pet> findByPetName(String petName);
    List<Pet> findAll();
}

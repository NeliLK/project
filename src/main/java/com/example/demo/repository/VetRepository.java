package com.example.demo.repository;

import com.example.demo.model.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    Vet findByVetName(String vetName);

    List<Vet> findAll();
}

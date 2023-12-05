package com.example.demo.repository;

import com.example.demo.model.entity.User;

import com.example.demo.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional <User>findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM User u JOIN u.roles r WHERE r.role = :role")
    boolean existsByRole(UserRoleEnum role);
}

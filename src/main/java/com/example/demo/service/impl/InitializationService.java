package com.example.demo.service.impl;

import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class InitializationService {

    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitializationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeAdminUser() {

        if (!userService.isAdminUserExists()) {

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("1234"));
            adminUser.setEmail("admin@email.com");


            UserRoleEnum adminRole = UserRoleEnum.ADMIN;
            UserRoleEntity adminRoleEntity = new UserRoleEntity().setRole(adminRole);

            UserRoleEnum userRole = UserRoleEnum.USER;
            UserRoleEntity userRoleEntity = new UserRoleEntity().setRole(userRole);


            List<UserRoleEntity> roles = new ArrayList<>(List.of(adminRoleEntity, userRoleEntity));

            adminUser.setRoles(roles);

            userService.registerAdmin(adminUser);
        }
    }
}


package com.example.demo.service.impl;

import com.example.demo.model.dto.UserRegistrationBindingModel;
import com.example.demo.model.dto.UserViewModel;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(UserRegistrationBindingModel userRegistrationBindingModel) {
        if (userRegistrationBindingModel == null) {
            return false;
        }

        String username = userRegistrationBindingModel.getUsername();
        if (this.userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Role not found: USER"));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userRegistrationBindingModel.getPassword()));
        user.setEmail(userRegistrationBindingModel.getEmail());
        user.setRoles(Collections.singletonList(userRole));

        this.userRepository.save(user);
        return true;
    }

    @Override
    public void registerAdmin(User user) {
        this.userRepository.save(user);
    }

    @Override
    public boolean isAdminUserExists() {
        return userRepository.existsByRole(UserRoleEnum.ADMIN);
    }

    @Override
    public void addRoleToUser(Long id, UserRoleEnum role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        UserRoleEntity roleEntity = new UserRoleEntity().setRole(role);
        user.getRoles().add(roleEntity);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(Long id, UserRoleEnum roleEnum) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        user.getRoles().removeIf(role -> role.getRole() == roleEnum);
        userRepository.save(user);
    }

    @Override
    public List<UserViewModel> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    UserViewModel userViewModel = new UserViewModel();
                    userViewModel.setId(user.getId());
                    userViewModel.setUsername(user.getUsername());
                    userViewModel.setEmail(user.getEmail());

                    List<UserRoleEnum> roleEnums = user.getRoles().stream()
                            .map(UserRoleEntity::getRole)
                            .collect(Collectors.toList());

                    userViewModel.setRoles(roleEnums);
                    return userViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void removeUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(id);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return userRepository.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
        } else {
            throw new IllegalStateException("User not authenticated");
        }
    }
}

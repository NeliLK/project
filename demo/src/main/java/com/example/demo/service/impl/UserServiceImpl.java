package com.example.demo.service.impl;

import com.example.demo.model.dto.UserLoginBindingModel;
import com.example.demo.model.dto.UserRegistrationBindingModel;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoggedUser;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final LoggedUser loggedUser;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, LoggedUser loggedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserRegistrationBindingModel userRegistrationBindingModel) {
        if (userRegistrationBindingModel == null) {
            return false;
        }

        String username = userRegistrationBindingModel.getUsername();
        if (this.userRepository.findByUsername(username) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userRegistrationBindingModel.getPassword()));
        user.setEmail(userRegistrationBindingModel.getEmail());

        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean login(UserLoginBindingModel userLoginBindingModel) {
        String username = userLoginBindingModel.getUsername();
        User user = userRepository.findByUsername(username);

        if (user != null
                && passwordEncoder.matches(userLoginBindingModel.getPassword(), user.getPassword())) {
            loggedUser.login(username);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        this.loggedUser.logout();
    }
}

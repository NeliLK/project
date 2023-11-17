package com.example.demo.service;

import com.example.demo.model.dto.UserLoginBindingModel;
import com.example.demo.model.dto.UserRegistrationBindingModel;

public interface UserService {
    boolean register(UserRegistrationBindingModel userRegistrationBindingModel);

    boolean login(UserLoginBindingModel userLoginBindingModel);

    void logout();
}

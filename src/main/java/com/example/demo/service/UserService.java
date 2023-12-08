package com.example.demo.service;

import com.example.demo.model.dto.UserViewModel;
import com.example.demo.model.dto.UserRegistrationBindingModel;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.UserRoleEnum;

import java.util.List;

public interface UserService {
    boolean register(UserRegistrationBindingModel userRegistrationBindingModel);

    void registerAdmin(User user);

    boolean isAdminUserExists();

    void addRoleToUser(Long id, UserRoleEnum role);

    void removeRoleFromUser(Long id, UserRoleEnum roleEnum);

    List<UserViewModel> getAllUsers();

    void removeUserById(Long id);

    User getLoggedInUser();

}

package com.example.demo.service.impl;

import com.example.demo.model.entity.User;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

class InitializationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private InitializationService initializationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void initializeAdminUser_whenAdminUserDoesNotExist_shouldCreateAdminUser() {
        // Arrange
        when(userService.isAdminUserExists()).thenReturn(false);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        UserRoleEnum adminRole = UserRoleEnum.ADMIN;
        UserRoleEnum userRole = UserRoleEnum.USER;

        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");

        // Act
        initializationService.initializeAdminUser();

        // Assert
        verify(userService, times(1)).registerAdmin(userCaptor.capture());
        User capturedAdminUser = userCaptor.getValue();

        assertEquals("admin", capturedAdminUser.getUsername());

    }

    @Test
    void initializeAdminUser_whenAdminUserExists_shouldNotCreateAdminUser() {
        // Arrange
        when(userService.isAdminUserExists()).thenReturn(true);

        // Act
        initializationService.initializeAdminUser();

        // Assert
        verify(userService, never()).registerAdmin(any());
    }
}

package com.example.demo.service.impl;

import com.example.demo.model.dto.UserRegistrationBindingModel;
import com.example.demo.model.dto.UserViewModel;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterSuccess() {
        // Arrange
        UserRegistrationBindingModel registrationModel = new UserRegistrationBindingModel();
        registrationModel.setUsername("newUser");
        registrationModel.setPassword("password");
        registrationModel.setEmail("newuser@example.com");

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(userRoleRepository.findByRole(UserRoleEnum.USER)).thenReturn(Optional.of(new UserRoleEntity()));

        // Act
        boolean result = userService.register(registrationModel);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserExists() {
        // Arrange
        UserRegistrationBindingModel registrationModel = new UserRegistrationBindingModel();
        registrationModel.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        // Act
        boolean result = userService.register(registrationModel);

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterNullModel() {
        // Act
        boolean result = userService.register(null);

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(createTestUser()));

        // Act
        List<UserViewModel> users = userService.getAllUsers();

        // Assert
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
    }

    private User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setRoles(Collections.singletonList(new UserRoleEntity()));
        return user;
    }
}

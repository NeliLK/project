package com.example.demo.service.impl;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @Test
    void testGetAllRoles() {
        // Arrange
        UserRoleEntity role1 = new UserRoleEntity();
        role1.setId(1L);
        role1.setRole(UserRoleEnum.valueOf("USER"));

        UserRoleEntity role2 = new UserRoleEntity();
        role2.setId(2L);
        role2.setRole(UserRoleEnum.valueOf("ADMIN"));

        List<UserRoleEntity> roles = Arrays.asList(role1, role2);

        when(userRoleRepository.findAll()).thenReturn(roles);

        // Act
        List<UserRoleEntity> result = userRoleService.getAllRoles();

        // Assert
        assertEquals(2, result.size());
        assertEquals(UserRoleEnum.valueOf("USER"), result.get(0).getRole());
        assertEquals(UserRoleEnum.valueOf("ADMIN"), result.get(1).getRole());
    }
}

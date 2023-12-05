package com.example.demo.service;

import com.example.demo.model.entity.UserRoleEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleService {
    @Transactional
    List<UserRoleEntity> getAllRoles();
}

package com.example.demo.service.impl;


import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class VetClinicUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public VetClinicUserDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsername(username)
        .map(VetClinicUserDetailsService::map)
        .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
  }

  private static UserDetails map(com.example.demo.model.entity.User user) {
    return User
        .withUsername(user.getUsername())
        .password(user.getPassword())
            .authorities(user.getRoles().stream().map(VetClinicUserDetailsService::map).toList())
            .build();
  }

  private static GrantedAuthority map(UserRoleEntity userRoleEntity) {
    return new SimpleGrantedAuthority(
        "ROLE_" + userRoleEntity.getRole().name()
    );
  }
}

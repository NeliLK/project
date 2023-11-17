package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user")
  private List<Pet> pets;

//  @ManyToMany(fetch = FetchType.EAGER)
//  @JoinTable(
//      name = "users_roles",
//      joinColumns = @JoinColumn(name = "user_id"),
//      inverseJoinColumns = @JoinColumn(name = "role_id"))
//  private List<UserRoleEntity> roles = new ArrayList<>();

//  private boolean active;


  public User() {
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }



  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Pet> getPets() {
    return pets;
  }

  public void setPets(List<Pet> pets) {
    this.pets = pets;
  }

//    public boolean isActive() {
//    return active;
//  }
//
//  public User setActive(boolean active) {
//    this.active = active;
//    return this;
//  }
//
//  public List<UserRoleEntity> getRoles() {
//    return roles;
//  }
//
//  public void setRoles(List<UserRoleEntity> roles) {
//    this.roles = roles;
//  }
}

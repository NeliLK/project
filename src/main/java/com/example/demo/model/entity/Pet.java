package com.example.demo.model.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(nullable = false)
    private String specie ;

    @Column(nullable = false)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pet")
    private List<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pet")
    private MedicalRecord medicalRecord;

    public Pet() {
    }

    public Pet(String petName, String specie, Integer age) {
        this.petName = petName;
        this.specie = specie;
        this.age = age;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}

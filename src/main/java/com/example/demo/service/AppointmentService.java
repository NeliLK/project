package com.example.demo.service;

import com.example.demo.model.dto.AppointmentAddBindingModel;
import com.example.demo.model.dto.AppointmentViewModel;

import java.util.List;

public interface AppointmentService {
    void addAppointment(AppointmentAddBindingModel appointment);

    List<AppointmentViewModel> getAppointmentsForUser();
}

package com.qwerty.core_service.controller;

import com.qwerty.core_service.dto.CreateAppointmentRequest;
import com.qwerty.core_service.dto.UpdateAppointmentRequest;
import com.qwerty.core_service.entity.Appointment;
import com.qwerty.core_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAppointments() {
        return appointmentService.getAppointments();
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }

    @PostMapping
    public Appointment addAppointment(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.addAppointment(request);
    }

    @PutMapping
    public Appointment updateAppointment(@RequestBody UpdateAppointmentRequest request) {
        return appointmentService.updateAppointment(request);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}

package com.qwerty.core_service.service;

import com.qwerty.core_service.dto.CreateAppointmentRequest;
import com.qwerty.core_service.dto.UpdateAppointmentRequest;
import com.qwerty.core_service.entity.Appointment;
import com.qwerty.core_service.exception.NotFoundException;
import com.qwerty.core_service.mapper.AppointmentMapper;
import com.qwerty.core_service.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Appointment with id " + id + " not found"));
    }

    public Appointment addAppointment(CreateAppointmentRequest request) {
        return appointmentRepository.save(appointmentMapper.toEntity(request));
    }

    public Appointment updateAppointment(UpdateAppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(request.id()).orElseThrow(
                () -> new NotFoundException("Appointment with id " + request.id() + " not found"));
        return appointmentRepository.save(appointmentMapper.partialUpdate(request, appointment));
    }

    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id))
            throw new NotFoundException("Appointment with id " + id + " not found");

        appointmentRepository.deleteById(id);
    }
}

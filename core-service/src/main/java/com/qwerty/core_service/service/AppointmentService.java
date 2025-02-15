package com.qwerty.core_service.service;

import com.qwerty.core_service.entity.Appointment;
import com.qwerty.core_service.exception.NotFoundException;
import com.qwerty.core_service.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAppointments(){
        return appointmentRepository.findAll();
    }

    public Appointment getAppointment(Long id){
        return appointmentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Appointment with id " + id + " not found"));
    }

    public Appointment addAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Appointment appointment){
        if(!appointmentRepository.existsById(appointment.getId()))
            throw new NotFoundException("Appointment with id " + appointment.getId() + " not found");

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id){
        if(!appointmentRepository.existsById(id))
            throw new NotFoundException("Appointment with id " + id + " not found");

        appointmentRepository.deleteById(id);
    }
}

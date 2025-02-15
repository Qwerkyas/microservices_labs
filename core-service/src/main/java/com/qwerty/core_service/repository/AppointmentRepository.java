package com.qwerty.core_service.repository;

import com.qwerty.core_service.entity.Appointment;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}

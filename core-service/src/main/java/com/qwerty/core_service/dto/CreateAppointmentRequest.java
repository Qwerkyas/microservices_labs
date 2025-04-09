package com.qwerty.core_service.dto;

import java.time.LocalDate;

public record CreateAppointmentRequest(String name, LocalDate dateOfBirth, Integer age, String doctor) {
}

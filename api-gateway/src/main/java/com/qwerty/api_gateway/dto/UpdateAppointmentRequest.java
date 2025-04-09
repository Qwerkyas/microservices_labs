package com.qwerty.api_gateway.dto;

import java.time.LocalDate;

public record UpdateAppointmentRequest(Long id, String name, LocalDate dateOfBirth, Integer age, String doctor) {
}

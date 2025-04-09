package com.qwerty.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CreateAppointmentRequest(String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth, Integer age, String doctor) {
}

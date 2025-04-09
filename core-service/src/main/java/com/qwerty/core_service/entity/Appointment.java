package com.qwerty.core_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    String name;

    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;

    @Column(name = "age", nullable = false)
    Integer age;

    @Column(name = "doctor", nullable = false)
    String doctor;
}

package com.qwerty.core_service.repository;

import com.qwerty.core_service.entity.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("save should save appointment when valid object is provided")
    void save_ShouldSaveAppointment_WhenValidObjectIsProvided() {
        Appointment appointment = Appointment.builder()
                .name("Проскурин Илья")
                .dateOfBirth(LocalDate.of(2003, 1, 15))
                .age(21)
                .doctor("Ардаков Игорь")
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        assertThat(savedAppointment.getId()).isNotNull();
        assertThat(savedAppointment.getName()).isEqualTo("Проскурин Илья");
    }

    @Test
    @DisplayName("findById should return appointment when appointment exists")
    void findById_ShouldReturnAppointment_WhenAppointmentExists() {
        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .name("Синявский Вячеслав")
                .dateOfBirth(LocalDate.of(1980, 3, 11))
                .age(35)
                .doctor("Ардаков Игорь")
                .build());

        Optional<Appointment> foundAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(foundAppointment.isPresent()).isTrue();
        assertThat(foundAppointment.orElseThrow()
                .getId()).isEqualTo(appointment.getId());
        assertThat(foundAppointment.get().getName()).isEqualTo("Синявский Вячеслав");
    }

    @Test
    @DisplayName("deleteById should delete appointment when appointment exists")
    void deleteById_ShouldDeleteAppointment_WhenAppointmentExists() {
        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .name("Родионов Сергей")
                .dateOfBirth(LocalDate.of(2005, 10, 10))
                .age(19)
                .doctor("Донченко Иван")
                .build());

        appointmentRepository.deleteById(appointment.getId());
        Optional<Appointment> deletedAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(deletedAppointment).isEmpty();
    }
}

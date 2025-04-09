package com.qwerty.api_gateway.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.qwerty.api_gateway.dto.CreateAppointmentRequest;
import com.qwerty.api_gateway.dto.AppointmentResponse;
import com.qwerty.api_gateway.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@WireMockTest(httpPort = 8081)
class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    @DisplayName("getAppointments_ShouldReturnListOfAppointments_WhenDataExists")
    void getAppointments_ShouldReturnListOfAppointments_WhenDataExists() {
        String responseBody = "[{\"id\":2,\"name\":\"Проскурин Илья\",\"dateOfBirth\":\"2003-01-15\",\"age\":\"21\",\"doctor\":\"Ардаков Игорь\"}]";
        stubFor(get("/api/appointments")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        List<AppointmentResponse> result = appointmentService.getAppointments();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).name()).isEqualTo("Проскурин Илья");
    }

    @Test
    @DisplayName("getAppointment_ShouldReturnAppointment_WhenItExists")
    void getAppointment_ShouldReturnAppointment_WhenItExists() {
        String responseBody = "{\"id\":1,\"name\":\"Синявский Вячеслав\",\"dateOfBirth\":\"1980-01-15\",\"age\":\"35\",\"doctor\":\"Ардаков Игорь\"}";
        stubFor(get("/api/appointments/1")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        AppointmentResponse result = appointmentService.getAppointment(1L);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Синявский Вяечеслав");
    }

    @Test
    @DisplayName("addAppointment_ShouldAddAppointment_WhenDataIsValid")
    void addAppointment_ShouldAddAppointment_WhenDataIsValid() {
        CreateAppointmentRequest request = new CreateAppointmentRequest("Родионов Сергей", LocalDate.of(2005, 10, 10), 19, "Донченко Иван");
        String requestBody = "{\"id\":1,\"name\":\"Родионов Сергей\",\"dateOfBirth\":\"2005-10-10\",\"age\":\"19\",\"doctor\":\"Донченко Иван\"}";
        String responseBody = "{\"id\":1,\"name\":\"Родионов Сергей\",\"dateOfBirth\":\"2005-10-10\",\"age\":\"19\",\"doctor\":\"Донченко Иван\"}";

        stubFor(post("/api/appointments")
                .withRequestBody(equalToJson(requestBody))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        AppointmentResponse result = appointmentService.addAppointment(request);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Родионов Сергей");
    }

    @Test
    @DisplayName("deleteAppointment_ShouldThrowNotFoundException_WhenAppointmentDoesNotExist")
    void deleteAppointment_ShouldThrowNotFoundException_WhenAppointmentDoesNotExist() {
        stubFor(delete("/api/appointments/99")
                .willReturn(WireMock.aResponse().withStatus(404)));

        assertThatThrownBy(() -> appointmentService.deleteAppointment(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Appointment with ID 99 not found");
    }
}

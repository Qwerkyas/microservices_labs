package com.qwerty.api_gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qwerty.api_gateway.dto.CreateAppointmentRequest;
import com.qwerty.api_gateway.dto.AppointmentResponse;
import com.qwerty.api_gateway.dto.UpdateAppointmentRequest;
import com.qwerty.api_gateway.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    @DisplayName("getAppointments_ShouldReturnListOfAppointments_WhenDataExists")
    void getAppointments_ShouldReturnListOfAppointments_WhenDataExists() throws Exception {
        List<AppointmentResponse> appointments = List.of(new AppointmentResponse(1L, "Проскурин Илья", LocalDate.of(2003, 1, 15), 21, "Ардаков Игорь"));
        when(appointmentService.getAppointments()).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(appointments)));
    }

    @Test
    @DisplayName("getAppointment_ShouldReturnAppointment_WhenItExists")
    void getAppointment_ShouldReturnAppointment_WhenItExists() throws Exception {
        AppointmentResponse appointment = new AppointmentResponse(1L, "Синявский Вячеслав", LocalDate.of(1980, 3, 11), 35, "Ардаков Игорь");
        when(appointmentService.getAppointment(1L)).thenReturn(appointment);

        mockMvc.perform(get("/api/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(appointment)));
    }

    @Test
    @DisplayName("addAppointment_ShouldAddAppointment_WhenDataIsValid")
    void addAppointment_ShouldAddAppointment_WhenDataIsValid() throws Exception {
        CreateAppointmentRequest request = new CreateAppointmentRequest("Родионов Сергей", LocalDate.of(2005, 10, 10), 19, "Донченко Иван");
        AppointmentResponse appointment = new AppointmentResponse(1L, "Родионов Сергей", LocalDate.of(2005, 10, 10), 19, "Донченко Иван");
        when(appointmentService.addAppointment(any(CreateAppointmentRequest.class))).thenReturn(appointment);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(appointment)));
    }

    @Test
    @DisplayName("updateAppointment_ShouldUpdateAppointment_WhenDataIsValid")
    void updateAppointment_ShouldUpdateAppointment_WhenDataIsValid() throws Exception {
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(1L, "Сыров Дмитрий", LocalDate.of(2001, 9, 11), 23, "Ардаков Игорь");
        AppointmentResponse updatedAppointment = new AppointmentResponse(1L, "Сыров Дмитрий", LocalDate.of(2001, 9, 11), 23, "Ардаков Игорь");
        when(appointmentService.updateAppointment(any(UpdateAppointmentRequest.class))).thenReturn(updatedAppointment);

        mockMvc.perform(put("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedAppointment)));
    }

    @Test
    @DisplayName("deleteAppointment_ShouldDeleteAppointment_WhenItExists")
    void deleteAppointment_ShouldDeleteAppointment_WhenItExists() throws Exception {
        doNothing().when(appointmentService).deleteAppointment(1L);

        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isNoContent());

        verify(appointmentService, times(1)).deleteAppointment(1L);
    }
}

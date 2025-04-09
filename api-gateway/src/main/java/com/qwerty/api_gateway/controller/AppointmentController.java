package com.qwerty.api_gateway.controller;

import com.qwerty.api_gateway.dto.CreateAppointmentRequest;
import com.qwerty.api_gateway.dto.AppointmentResponse;
import com.qwerty.api_gateway.dto.UpdateAppointmentRequest;
import com.qwerty.api_gateway.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Tag(name = "Встречи", description = "Эндпоинты для встречь")
public class AppointmentController {

    @Operation(summary = "Получить список встреч")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список встреч успешно получен"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getAppointments() {
        return appointmentService.getAppointments();
    }

    private final AppointmentService appointmentService;


    @Operation(summary = "Получить данные по встрече по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Встреча найдена"),
            @ApiResponse(responseCode = "404", description = "Встреча не найдена"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponse getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }

    @Operation(summary = "Добавить новую встречу")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Встреча успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse addAppointment(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.addAppointment(request);
    }

    @Operation(summary = "Обновить данные встречи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные встречи обновлены"),
            @ApiResponse(responseCode = "404", description = "Встреча не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponse updateAppointment(@RequestBody UpdateAppointmentRequest request) {
        return appointmentService.updateAppointment(request);
    }

    @Operation(summary = "Удалить встречи по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Встреча удален"),
            @ApiResponse(responseCode = "404", description = "Встреча не найдена"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}

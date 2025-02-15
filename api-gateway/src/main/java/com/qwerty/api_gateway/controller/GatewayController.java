package com.qwerty.api_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/appointment")
public class GatewayController {

    private final WebClient webClient;

    public GatewayController() {
        this.webClient = WebClient.create("http://core-service:8081");
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> getAppointment(@PathVariable Long id) {
        return webClient.get()
                .uri("/api/appointment/" + id)
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping
    public Mono<ResponseEntity<String>> addAppointment(@RequestBody String Appointment) {
        return webClient.post()
                .uri("/api/appointment")
                .bodyValue(Appointment)
                .retrieve()
                .toEntity(String.class);
    }

    @PutMapping
    public Mono<ResponseEntity<String>> updateAppointment(@RequestBody String Appointment) {
        return webClient.put()
                .uri("/api/appointment")
                .bodyValue(Appointment)
                .retrieve()
                .toEntity(String.class);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAppointment(@PathVariable Long id) {
        return webClient.delete()
                .uri("/api/appointment/" + id)
                .retrieve()
                .toBodilessEntity();
    }
}

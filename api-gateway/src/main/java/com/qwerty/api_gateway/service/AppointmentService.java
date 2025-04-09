package com.qwerty.api_gateway.service;

import com.qwerty.api_gateway.dto.CreateAppointmentRequest;
import com.qwerty.api_gateway.dto.AppointmentResponse;
import com.qwerty.api_gateway.dto.UpdateAppointmentRequest;
import com.qwerty.api_gateway.exception.CoreServiceException;
import com.qwerty.api_gateway.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final RestTemplate restTemplate;
    @Value("${core-service.url}/appointment")
    private String coreServiceAppointmentsApiUrl;

    public List<AppointmentResponse> getAppointments() {
        try {
            ResponseEntity<AppointmentResponse[]> response = restTemplate.getForEntity(coreServiceAppointmentsApiUrl, AppointmentResponse[].class);
            return List.of(Objects.requireNonNull(response.getBody()));
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when getting the list of appointment", e);
        }
    }

    public AppointmentResponse getAppointment(Long id) {
        try {
            return restTemplate.getForObject(coreServiceAppointmentsApiUrl + "/" + id, AppointmentResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Appointment with ID " + id + " not found");
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when receiving a appointment", e);
        }
    }

    public AppointmentResponse addAppointment(CreateAppointmentRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateAppointmentRequest> entity = new HttpEntity<>(request, headers);
            return restTemplate.postForObject(coreServiceAppointmentsApiUrl, entity, AppointmentResponse.class);
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when adding a appointment", e);
        }
    }

    public AppointmentResponse updateAppointment(UpdateAppointmentRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UpdateAppointmentRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<AppointmentResponse> response = restTemplate.exchange(coreServiceAppointmentsApiUrl, HttpMethod.PUT, entity, AppointmentResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Appointment with ID " + request.id() + " not found");
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when updating a appointment", e);
        }
    }

    public void deleteAppointment(Long id) {
        try {
            restTemplate.delete(coreServiceAppointmentsApiUrl + "/" + id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Appointment with ID " + id + " not found");
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when deleting a appointment", e);
        }
    }
}

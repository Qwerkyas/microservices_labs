package com.qwerty.core_service.mapper;

import com.qwerty.core_service.dto.CreateAppointmentRequest;
import com.qwerty.core_service.entity.Appointment;
import com.qwerty.core_service.dto.UpdateAppointmentRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {
    Appointment toEntity(CreateAppointmentRequest createAppointmentRequest);

    CreateAppointmentRequest toCreateAppointmentDto(Appointment appointment);

    Appointment toEntity(UpdateAppointmentRequest updateAppointmentRequest);

    UpdateAppointmentRequest toUpdateAppointmentRequest(Appointment appointment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Appointment partialUpdate(UpdateAppointmentRequest appointmentRequest, @MappingTarget Appointment user);
}

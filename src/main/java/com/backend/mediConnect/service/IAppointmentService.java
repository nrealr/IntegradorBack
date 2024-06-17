package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.AppointmentInputDto;
import com.backend.mediConnect.dto.output.AppointmentOutputDto;
import com.backend.mediConnect.dto.update.AppointmentUpdateDto;

import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    AppointmentOutputDto scheduleAppointment(AppointmentInputDto inputDto);
    List<AppointmentOutputDto> getAllAppointments();
    AppointmentOutputDto getAppointmentById(Long id);
    AppointmentOutputDto updateAppointment(Long id, AppointmentInputDto appointmentInputDto);
    void cancelAppointment(Long id);
    void deleteAppointment(Long id);
}

package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.AppointmentInputDto;
import com.backend.mediConnect.dto.output.AppointmentOutputDto;
import com.backend.mediConnect.dto.update.AppointmentUpdateDto;
import com.backend.mediConnect.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    AppointmentOutputDto scheduleAppointment(AppointmentInputDto inputDto);
    List<AppointmentOutputDto> getAllAppointments();
    AppointmentOutputDto getAppointmentById(Long id);
    AppointmentOutputDto updateAppointment(Long id, AppointmentInputDto appointmentInputDto);
    List<Appointment> getAppointmentsByDoctorAndTimeRange(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
    void cancelAppointment(Long id);

    List<AppointmentOutputDto> getAppointmentsByUserId(Long userId);

    void deleteAppointment(Long id);
}

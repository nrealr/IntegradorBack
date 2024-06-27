package com.backend.mediConnect.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentInputDto {

    @NotNull(message = "Doctor field cannot be null")
    private Long doctorId;

    @NotNull(message = "Patient field cannot be null")
    private Long patientId;

    @NotNull(message = "StartTime field cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "EndTime field cannot be null")
    private LocalDateTime endTime;

    public AppointmentInputDto() {
    }

    public AppointmentInputDto(Long doctorId, Long patientId, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

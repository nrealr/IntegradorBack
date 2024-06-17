package com.backend.mediConnect.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityInputDto {

    @NotNull(message = "Doctor field cannot be null")
    private Long doctorId;

    @NotNull(message = "StartTime field cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "EndTime field cannot be null")
    private LocalDateTime endTime;

    public AvailabilityInputDto() {
    }

    public AvailabilityInputDto(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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
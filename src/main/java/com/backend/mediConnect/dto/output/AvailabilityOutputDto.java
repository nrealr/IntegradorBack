package com.backend.mediConnect.dto.output;

import java.time.LocalDateTime;

public class AvailabilityOutputDto {

    private Long id;
    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailabilityOutputDto() {
    }

    public AvailabilityOutputDto(Long id, Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

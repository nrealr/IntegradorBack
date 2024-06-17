package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentUpdateDto {

    @NotNull(message = "ID field cannot be null")
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public AppointmentUpdateDto() {
    }

    public AppointmentUpdateDto(Long id, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

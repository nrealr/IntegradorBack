package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AvailabilityUpdateDto {

    @NotNull(message = "ID field cannot be null")
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long statusId;

    public AvailabilityUpdateDto() {
    }


    public AvailabilityUpdateDto(Long id, LocalDateTime endTime, LocalDateTime startTime, Long statusId) {
        this.id = id;
        this.endTime = endTime;
        this.startTime = startTime;
        this.statusId = statusId;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}

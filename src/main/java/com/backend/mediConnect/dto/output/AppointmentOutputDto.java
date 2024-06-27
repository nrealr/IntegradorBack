package com.backend.mediConnect.dto.output;

import com.backend.mediConnect.entity.Status;

import java.time.LocalDateTime;

public class AppointmentOutputDto {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public AppointmentOutputDto() {
    }

    public AppointmentOutputDto(Long id, Long doctorId, Long patientId, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

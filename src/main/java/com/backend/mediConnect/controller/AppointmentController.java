package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.input.AppointmentInputDto;
import com.backend.mediConnect.dto.output.AppointmentOutputDto;
import com.backend.mediConnect.entity.Appointment;
import com.backend.mediConnect.service.impl.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<AppointmentOutputDto> createAppointment(@RequestBody AppointmentInputDto appointmentInputDto) {
        AppointmentOutputDto createdAppointment = appointmentService.scheduleAppointment(appointmentInputDto);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AppointmentOutputDto>> getAllAppointments() {
        List<AppointmentOutputDto> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentOutputDto> getAppointmentById(@PathVariable Long id) {
        AppointmentOutputDto appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentOutputDto> updateAppointment(@PathVariable Long id,
                                                                  @RequestBody AppointmentInputDto appointmentInputDto) {
        AppointmentOutputDto updatedAppointment = appointmentService.updateAppointment(id, appointmentInputDto);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}/time-range")
    public List<Appointment> getAppointmentsByDoctorAndTimeRange(@PathVariable Long doctorId,
                                                                 @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                                                 @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return appointmentService.getAppointmentsByDoctorAndTimeRange(doctorId, startTime, endTime);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public List<AppointmentOutputDto> getAppointmentsByUserId(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }
}

package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.AppointmentInputDto;
import com.backend.mediConnect.dto.output.AppointmentOutputDto;
import com.backend.mediConnect.entity.Appointment;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.entity.Patient;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.repository.AppointmentRepository;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.repository.PatientRepository;
import com.backend.mediConnect.repository.UserRepository;
import com.backend.mediConnect.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public AppointmentOutputDto scheduleAppointment(AppointmentInputDto inputDto) {
        Doctor doctor = doctorRepository.findById(inputDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Patient patient = patientRepository.findById(inputDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));


        boolean isAvailable = appointmentRepository.findByDoctorIdAndTimeRange(
                inputDto.getDoctorId(), inputDto.getStartTime(), inputDto.getEndTime()).isEmpty();

        if (!isAvailable) {
            throw new IllegalArgumentException("Doctor is not available at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartTime(inputDto.getStartTime());
        appointment.setEndTime(inputDto.getEndTime());
        appointment.setStatus("scheduled");

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToOutputDto(savedAppointment);
    }

    @Override
    public List<AppointmentOutputDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentOutputDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        return convertToOutputDto(appointment);
    }

    @Override
    public AppointmentOutputDto updateAppointment(Long id, AppointmentInputDto appointmentInputDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        Doctor doctor = doctorRepository.findById(appointmentInputDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Patient patient = patientRepository.findById(appointmentInputDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartTime(appointmentInputDto.getStartTime());
        appointment.setEndTime(appointmentInputDto.getEndTime());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return convertToOutputDto(updatedAppointment);
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        appointment.setStatus("cancelled");
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    private AppointmentOutputDto convertToOutputDto(Appointment appointment) {
        AppointmentOutputDto outputDto = new AppointmentOutputDto();
        outputDto.setId(appointment.getId());
        outputDto.setDoctorId(appointment.getDoctor().getId());
        outputDto.setPatientId(appointment.getPatient().getId());
        outputDto.setStartTime(appointment.getStartTime());
        outputDto.setEndTime(appointment.getEndTime());
        outputDto.setStatus(appointment.getStatus());
        return outputDto;
    }



}

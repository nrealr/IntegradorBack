package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.AppointmentInputDto;
import com.backend.mediConnect.dto.output.AppointmentOutputDto;
import com.backend.mediConnect.entity.*;
import com.backend.mediConnect.repository.*;
import com.backend.mediConnect.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private EmailService emailService;

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

        Status scheduledStatus = statusRepository.findByName("scheduled")
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        appointment.setStatus(scheduledStatus);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Actualizar disponibilidad
        availabilityService.updateAvailabilityAfterAppointment(inputDto.getDoctorId(), inputDto.getStartTime(), inputDto.getEndTime());

        // Enviar correo de confirmación
        sendAppointmentConfirmationEmail(patient, doctor, savedAppointment);

        return convertToOutputDto(savedAppointment);
    }

    private void sendAppointmentConfirmationEmail(Patient patient, Doctor doctor, Appointment appointment) {
        String subject = "Appointment Confirmation";
        String text = "Dear " + patient.getUser().getName() + " " + patient.getUser().getLastname() + ",\n\n" +
                "Your appointment with Dr. " + doctor.getName() + " " + doctor.getLastname() + " " +
                "has been scheduled successfully.\n\n" +
                "Appointment Details:\n" +
                "Date: " + appointment.getStartTime().toLocalDate() + "\n" +
                "Time: " + appointment.getStartTime().toLocalTime() + " - " + appointment.getEndTime().toLocalTime() + "\n" +
                "Doctor: " + doctor.getName() + " " + doctor.getLastname() + "\n\n" +
                "Thank you for choosing MediConnect.\n\n" +
                "Best regards,\n" +
                "The MediConnect Team";

        emailService.sendEmail(patient.getUser().getEmail(), subject, text);
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

        // Enviar correo de actualización
        sendAppointmentUpdateEmail(patient, doctor, updatedAppointment);
        return convertToOutputDto(updatedAppointment);
    }

    private void sendAppointmentUpdateEmail(Patient patient, Doctor doctor, Appointment appointment) {
        String subject = "Appointment Update";
        String text = "Dear " + patient.getUser().getName() + " " + patient.getUser().getLastname() + ",\n\n" +
                "Your appointment with Dr. " + doctor.getName() + " " + doctor.getLastname() + " " +
                "has been updated successfully.\n\n" +
                "Updated Appointment Details:\n" +
                "Date: " + appointment.getStartTime().toLocalDate() + "\n" +
                "Time: " + appointment.getStartTime().toLocalTime() + " - " + appointment.getEndTime().toLocalTime() + "\n" +
                "Doctor: " + doctor.getName() + " " + doctor.getLastname() + "\n\n" +
                "Thank you for choosing MediConnect.\n\n" +
                "Best regards,\n" +
                "The MediConnect Team";

        emailService.sendEmail(patient.getUser().getEmail(), subject, text);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndTimeRange(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        return appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startTime, endTime);
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));


        Status cancelledStatus = statusRepository.findByName("cancelled")
                .orElseThrow(() -> new IllegalArgumentException("Status 'cancelled' not found"));


        appointment.setStatus(cancelledStatus);
        appointmentRepository.save(appointment);


        List<Availability> availabilities = availabilityRepository.findByDoctorIdAndTimeRange(
                appointment.getDoctor().getId(), appointment.getStartTime(), appointment.getEndTime());

        for (Availability availability : availabilities) {

            if (availability.getStartTime().equals(appointment.getStartTime()) &&
                    availability.getEndTime().equals(appointment.getEndTime())) {


                Status availableStatus = statusRepository.findByName("available")
                        .orElseThrow(() -> new IllegalArgumentException("Status 'available' not found"));


                availability.setStatus(availableStatus);
                availabilityRepository.save(availability);

                break;
            }
        }
        // Enviar correo de cancelación
        sendAppointmentCancellationEmail(appointment.getPatient(), appointment.getDoctor(), appointment);
    }

    private void sendAppointmentCancellationEmail(Patient patient, Doctor doctor, Appointment appointment) {
        String subject = "Appointment Cancellation";
        String text = "Dear " + patient.getUser().getName() + " " + patient.getUser().getLastname() + ",\n\n" +
                "We regret to inform you that your appointment with Dr. " + doctor.getName() + " " + doctor.getLastname() + " " +
                "has been cancelled.\n\n" +
                "Appointment Details:\n" +
                "Date: " + appointment.getStartTime().toLocalDate() + "\n" +
                "Time: " + appointment.getStartTime().toLocalTime() + " - " + appointment.getEndTime().toLocalTime() + "\n" +
                "Doctor: " + doctor.getName() + " " + doctor.getLastname() + "\n\n" +
                "If you have any questions, please contact us.\n\n" +
                "Best regards,\n" +
                "The MediConnect Team";

        emailService.sendEmail(patient.getUser().getEmail(), subject, text);
    }

    @Override
    public List<AppointmentOutputDto> getAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(userId);
        return appointments.stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
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
        outputDto.setStatus(appointment.getStatus().getName());
        return outputDto;
    }
}



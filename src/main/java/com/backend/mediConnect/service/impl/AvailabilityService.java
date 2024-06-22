package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.AvailabilityInputDto;
import com.backend.mediConnect.dto.output.AvailabilityOutputDto;
import com.backend.mediConnect.dto.update.AvailabilityUpdateDto;
import com.backend.mediConnect.entity.Availability;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.entity.Status;
import com.backend.mediConnect.repository.AvailabilityRepository;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.repository.StatusRepository;
import com.backend.mediConnect.service.IAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailabilityService implements IAvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public AvailabilityOutputDto createAvailability(AvailabilityInputDto inputDto) {
        Doctor doctor = doctorRepository.findById(inputDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Status status = statusRepository.findByName("available")
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));

        Availability availability = new Availability();
        availability.setDoctor(doctor);
        availability.setStartTime(inputDto.getStartTime());
        availability.setEndTime(inputDto.getEndTime());
        availability.setStatus(status);

        Availability savedAvailability = availabilityRepository.save(availability);
        return mapToAvailabilityOutputDto(savedAvailability);
    }

    @Override
    public Optional<AvailabilityOutputDto> getAvailabilityById(Long id) {
        return availabilityRepository.findById(id).map(this::mapToAvailabilityOutputDto);
    }

    @Override
    public Optional<AvailabilityOutputDto> updateAvailability(Long id, AvailabilityUpdateDto availabilityUpdateDto) {
        return availabilityRepository.findById(id)
                .map(availability -> {
                    availability.setStartTime(availabilityUpdateDto.getStartTime());
                    availability.setEndTime(availabilityUpdateDto.getEndTime());
                    Availability updatedAvailability = availabilityRepository.save(availability);
                    return mapToAvailabilityOutputDto(updatedAvailability);
                });
    }

    @Override
    public List<AvailabilityOutputDto> getAllAvailabilities() {
        return availabilityRepository.findAll().stream()
                .map(this::mapToAvailabilityOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityOutputDto> getAvailabilitiesByDoctorId(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToAvailabilityOutputDto)
                .collect(Collectors.toList());
    }

    public void updateAvailabilityAfterAppointment(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Status reservedStatus = statusRepository.findByName("reserved")
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        Status availableStatus = statusRepository.findByName("available")
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));

        List<Availability> availabilities = availabilityRepository.findByDoctorIdAndTimeRange(doctorId, startTime, endTime);

        for (Availability availability : availabilities) {
            if (availability.getStartTime().isBefore(startTime) && availability.getEndTime().isAfter(endTime)) {
                // Split the availability into three parts: before, during, and after the appointment

                Availability newAvailabilityBefore = new Availability();
                newAvailabilityBefore.setDoctor(doctor);
                newAvailabilityBefore.setStartTime(availability.getStartTime());
                newAvailabilityBefore.setEndTime(startTime);
                newAvailabilityBefore.setStatus(availableStatus);
                availabilityRepository.save(newAvailabilityBefore);

                Availability reservedAvailability = new Availability();
                reservedAvailability.setDoctor(doctor);
                reservedAvailability.setStartTime(startTime);
                reservedAvailability.setEndTime(endTime);
                reservedAvailability.setStatus(reservedStatus);
                availabilityRepository.save(reservedAvailability);

                Availability newAvailabilityAfter = new Availability();
                newAvailabilityAfter.setDoctor(doctor);
                newAvailabilityAfter.setStartTime(endTime);
                newAvailabilityAfter.setEndTime(availability.getEndTime());
                newAvailabilityAfter.setStatus(availableStatus);
                availabilityRepository.save(newAvailabilityAfter);

                availabilityRepository.delete(availability);
            } else if (availability.getStartTime().isBefore(startTime)) {
                // Only split into two parts: before the appointment, and during the appointment

                availability.setEndTime(startTime);
                availability.setStatus(availableStatus);
                availabilityRepository.save(availability);

                Availability reservedAvailability = new Availability();
                reservedAvailability.setDoctor(doctor);
                reservedAvailability.setStartTime(startTime);
                reservedAvailability.setEndTime(endTime);
                reservedAvailability.setStatus(reservedStatus);
                availabilityRepository.save(reservedAvailability);
            } else if (availability.getEndTime().isAfter(endTime)) {
                // Only split into two parts: during the appointment, and after the appointment

                availability.setStartTime(endTime);
                availability.setStatus(availableStatus);
                availabilityRepository.save(availability);

                Availability reservedAvailability = new Availability();
                reservedAvailability.setDoctor(doctor);
                reservedAvailability.setStartTime(startTime);
                reservedAvailability.setEndTime(endTime);
                reservedAvailability.setStatus(reservedStatus);
                availabilityRepository.save(reservedAvailability);
            } else {
                // Exact match for the appointment time
                availability.setStatus(reservedStatus);
                availabilityRepository.save(availability);
            }
        }
    }

    @Override
    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

    private AvailabilityOutputDto mapToAvailabilityOutputDto(Availability availability) {
        AvailabilityOutputDto dto = new AvailabilityOutputDto();
        dto.setId(availability.getId());
        dto.setDoctorId(availability.getDoctor().getId());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        dto.setStatus(availability.getStatus().getName());
        return dto;
    }

    @Override
    public List<Availability> getTakenTimeSlots(Long doctorId, LocalDate date) {
        return availabilityRepository.findByDoctorIdAndDateAndStatus(doctorId, date, "reserved");
    }


    @Override
    public List<LocalDate> getAvailableDaysByDoctorId(Long doctorId) {
        List<Availability> availabilities = availabilityRepository.findByDoctorId(doctorId);

        List<LocalDate> availableDates = availabilities.stream()
                .map(availability -> availability.getStartTime().toLocalDate())
                .distinct() // Obtener fechas únicas
                .collect(Collectors.toList());

        return availableDates;
    }

    @Override
    public List<AvailabilityOutputDto> getAvailabilitiesByDoctorIdAndDate(Long doctorId, LocalDate date) {
        List<Availability> availabilities = availabilityRepository.findByDoctorIdAndDate(doctorId, date);

        return availabilities.stream()
                .map(this::mapToAvailabilityOutputDto)
                .collect(Collectors.toList());
    }
}


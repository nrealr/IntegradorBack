package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.AvailabilityInputDto;
import com.backend.mediConnect.dto.output.AvailabilityOutputDto;
import com.backend.mediConnect.dto.update.AvailabilityUpdateDto;
import com.backend.mediConnect.entity.Availability;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.repository.AvailabilityRepository;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.service.IAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public AvailabilityOutputDto createAvailability(AvailabilityInputDto inputDto) {
        Doctor doctor = doctorRepository.findById(inputDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Availability availability = new Availability();
        availability.setDoctor(doctor);
        availability.setStartTime(inputDto.getStartTime());
        availability.setEndTime(inputDto.getEndTime());

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

    public void updateAvailabilityAfterAppointment(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        List<Availability> availabilities = availabilityRepository.findByDoctorIdAndTimeRange(doctorId, startTime, endTime);

        for (Availability availability : availabilities) {
            availabilityRepository.delete(availability);

            if (availability.getStartTime().isBefore(startTime)) {
                Availability newAvailabilityBefore = new Availability();
                newAvailabilityBefore.setDoctor(doctor);
                newAvailabilityBefore.setStartTime(availability.getStartTime());
                newAvailabilityBefore.setEndTime(startTime);
                availabilityRepository.save(newAvailabilityBefore);
            }

            if (availability.getEndTime().isAfter(endTime)) {
                Availability newAvailabilityAfter = new Availability();
                newAvailabilityAfter.setDoctor(doctor);
                newAvailabilityAfter.setStartTime(endTime);
                newAvailabilityAfter.setEndTime(availability.getEndTime());
                availabilityRepository.save(newAvailabilityAfter);
            }
        }
    }

    public List<AvailabilityOutputDto> getAllAvailabilities() {
        return availabilityRepository.findAll().stream()
                .map(this::mapToAvailabilityOutputDto)
                .collect(Collectors.toList());
    }

    public List<AvailabilityOutputDto> getAvailabilitiesByDoctorId(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToAvailabilityOutputDto)
                .collect(Collectors.toList());
    }

    private AvailabilityOutputDto mapToAvailabilityOutputDto(Availability availability) {
        AvailabilityOutputDto dto = new AvailabilityOutputDto();
        dto.setId(availability.getId());
        dto.setDoctorId(availability.getDoctor().getId());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        return dto;
    }
}

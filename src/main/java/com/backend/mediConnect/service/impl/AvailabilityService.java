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

import java.util.Optional;

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

    private AvailabilityOutputDto mapToAvailabilityOutputDto(Availability availability) {
        AvailabilityOutputDto dto = new AvailabilityOutputDto();
        dto.setId(availability.getId());
        dto.setDoctorId(availability.getDoctor().getId());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        return dto;
    }
}

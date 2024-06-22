package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.AvailabilityInputDto;
import com.backend.mediConnect.dto.output.AvailabilityOutputDto;
import com.backend.mediConnect.dto.update.AvailabilityUpdateDto;

import java.util.List;
import java.util.Optional;

public interface IAvailabilityService {

    AvailabilityOutputDto createAvailability(AvailabilityInputDto availabilityInputDto);
    Optional<AvailabilityOutputDto> getAvailabilityById(Long id);
    Optional<AvailabilityOutputDto> updateAvailability(Long id, AvailabilityUpdateDto availabilityUpdateDto);

    List<AvailabilityOutputDto> getAllAvailabilities();

    List<AvailabilityOutputDto> getAvailabilitiesByDoctorId(Long doctorId);

    void deleteAvailability(Long id);
}

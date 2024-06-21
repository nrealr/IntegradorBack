package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.input.AvailabilityInputDto;
import com.backend.mediConnect.dto.output.AvailabilityOutputDto;
import com.backend.mediConnect.dto.update.AvailabilityUpdateDto;
import com.backend.mediConnect.service.impl.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/add")
    public ResponseEntity<AvailabilityOutputDto> createAvailability(@Valid @RequestBody AvailabilityInputDto inputDto) {
        AvailabilityOutputDto createdAvailability = availabilityService.createAvailability(inputDto);
        return new ResponseEntity<>(createdAvailability, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityOutputDto> getAvailabilityById(@PathVariable Long id) {
        Optional<AvailabilityOutputDto> availability = availabilityService.getAvailabilityById(id);
        return availability.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityOutputDto> updateAvailability(@PathVariable Long id,
                                                                    @Valid @RequestBody AvailabilityUpdateDto availabilityUpdateDto) {
        Optional<AvailabilityOutputDto> updatedAvailability = availabilityService.updateAvailability(id, availabilityUpdateDto);
        return updatedAvailability.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<AvailabilityOutputDto>> getAllAvailabilities() {
        List<AvailabilityOutputDto> availabilities = availabilityService.getAllAvailabilities();
        return new ResponseEntity<>(availabilities, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AvailabilityOutputDto>> getAvailabilitiesByDoctorId(@PathVariable Long doctorId) {
        List<AvailabilityOutputDto> availabilities = availabilityService.getAvailabilitiesByDoctorId(doctorId);
        return new ResponseEntity<>(availabilities, HttpStatus.OK);
    }
}

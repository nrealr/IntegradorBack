package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.input.LocationInputDto;
import com.backend.mediConnect.dto.output.LocationOutputDto;
import com.backend.mediConnect.dto.update.LocationUpdateDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.service.impl.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/register")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<LocationOutputDto> addLocation(@RequestBody @Valid LocationInputDto location) {
        return new ResponseEntity<>(locationService.addLocation(location), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<LocationOutputDto>> listLocations(){
        return new ResponseEntity<>(locationService.listLocations(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<LocationOutputDto> findLocationById(@PathVariable Long id) {
        return new ResponseEntity<>(locationService.findLocationById(id), HttpStatus.OK);
    }


    @PutMapping(value = "/update/{id}")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<LocationOutputDto> updateLocation (@ModelAttribute @Validated LocationUpdateDto location) throws IOException, ResourceNotFoundException {
        return new ResponseEntity<>(locationService.updateLocation(location), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> deleteLocation (@PathVariable Long id) throws ResourceNotFoundException {
        locationService.deleteLocation(id);
        return new ResponseEntity<>("Location successfully deleted", HttpStatus.NO_CONTENT);
    }
}

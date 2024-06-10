package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.LocationInputDto;
import com.backend.mediConnect.dto.output.LocationOutputDto;
import com.backend.mediConnect.dto.update.LocationUpdateDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ILocationService {
    LocationOutputDto addLocation(LocationInputDto location);
    List<LocationOutputDto> listLocations();
    LocationOutputDto findLocationById(Long id);
    LocationOutputDto updateLocation(LocationUpdateDto specialty) throws ResourceNotFoundException, IOException;
    void deleteLocation(Long id) throws ResourceNotFoundException;
    List<LocationOutputDto> searchLocations(String name);
}

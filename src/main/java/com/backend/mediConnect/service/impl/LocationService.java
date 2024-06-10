package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.LocationInputDto;
import com.backend.mediConnect.dto.output.LocationOutputDto;
import com.backend.mediConnect.dto.update.LocationUpdateDto;
import com.backend.mediConnect.entity.Location;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.LocationRepository;
import com.backend.mediConnect.service.ILocationService;
import com.backend.mediConnect.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService implements ILocationService {

    private final Logger LOGGER = LoggerFactory.getLogger(SpecialtyService.class);
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    private final JsonPrinter jsonPrinter;

    public LocationService(LocationRepository locationRepository, ModelMapper modelMapper, JsonPrinter jsonPrinter) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
        this.jsonPrinter = jsonPrinter;
    }

    @Override
    public LocationOutputDto addLocation(LocationInputDto location) {
        LOGGER.info("Location information received " + jsonPrinter.toString(location));
        Location locationEntity = modelMapper.map(location, Location.class);

        Location locationPersisted = locationRepository.save(locationEntity);

        LocationOutputDto locationOutputDto = modelMapper.map(locationPersisted, LocationOutputDto.class);

        LOGGER.info("Location saved: " + jsonPrinter.toString(locationOutputDto));

        return locationOutputDto;
    }

    @Override
    public List<LocationOutputDto> listLocations() {
        List<LocationOutputDto> locations = locationRepository.findAll()
                .stream()
                .map(location -> modelMapper.map(location, LocationOutputDto.class))
                .toList();

        LOGGER.info("List of all locations; {}", jsonPrinter.toString((locations)));

        return locations;
    }

    @Override
    public LocationOutputDto findLocationById(Long id) {
        Location locationSearched = locationRepository.findById(id).orElse(null);
        LocationOutputDto locationFound = null;

        if (locationSearched != null){
            locationFound = modelMapper.map(locationSearched, LocationOutputDto.class);
            LOGGER.info("Location found: {}", jsonPrinter.toString(locationFound));
        } else {
            LOGGER.error("The id is not registered on the database.");
        }
        return locationFound;
    }

    @Override
    public LocationOutputDto updateLocation(LocationUpdateDto location) throws ResourceNotFoundException, IOException {
        Location locationToUpdate = locationRepository.findById(location.getId()).orElse(null);

        if (locationToUpdate == null) {
            LOGGER.error("The location was not found in the database. Update failed.");
            throw new ResourceNotFoundException("Location not found");
        }

        locationToUpdate.setName(location.getName());
        locationToUpdate.setAddress(location.getAddress());

        Location locationUpdated = locationRepository.save(locationToUpdate);
        LocationOutputDto locationOutputDto = modelMapper.map(locationUpdated, LocationOutputDto.class);

        LOGGER.warn("Specialty updated: {}", jsonPrinter.toString(locationOutputDto));
        return locationOutputDto;
    }

    @Override
    public void deleteLocation(Long id) throws ResourceNotFoundException {
        if (locationRepository.findById(id).orElse(null) != null){
            locationRepository.deleteById(id);
            LOGGER.warn("Location with id {} was deleted", id);
        }else {
            LOGGER.error("Couldn't find location with id {}", id);
            throw new ResourceNotFoundException("Couldn't find location with id " + id);
        }
    }

    @Override
    public List<LocationOutputDto> searchLocations(String name) {
        List<Location> locations = locationRepository.findByNameContainingIgnoreCase(name);
        if (locations.isEmpty()) {
            LOGGER.warn("No locations found with name containing: {}", name);
        }
        return locations.stream()
                .map(location -> modelMapper.map(location, LocationOutputDto.class))
                .collect(Collectors.toList());
    }
}

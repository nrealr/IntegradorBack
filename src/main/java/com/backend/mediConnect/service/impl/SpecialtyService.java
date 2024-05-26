package com.backend.mediConnect.service.impl;
import com.backend.mediConnect.dto.input.SpecialtyInputDto;
import com.backend.mediConnect.dto.output.SpecialtyOutputDto;
import com.backend.mediConnect.dto.update.SpecialtyUpdateDto;
import com.backend.mediConnect.entity.Specialty;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.SpecialtyRepository;
import com.backend.mediConnect.service.ISpecialtyService;
import com.backend.mediConnect.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService implements ISpecialtyService {
    private final Logger LOGGER = LoggerFactory.getLogger(SpecialtyService.class);
    private final SpecialtyRepository specialtyRepository;
    private final ModelMapper modelMapper;
    private final JsonPrinter jsonPrinter;

    public SpecialtyService(SpecialtyRepository specialtyRepository, ModelMapper modelMapper, JsonPrinter jsonPrinter) {
        this.specialtyRepository = specialtyRepository;
        this.modelMapper = modelMapper;
        this.jsonPrinter = jsonPrinter;
    }

    @Override
    public SpecialtyOutputDto registerSpecialty(SpecialtyInputDto specialty) {

        LOGGER.info("Specialty information received " + jsonPrinter.toString(specialty));
        Specialty specialtyEntity = modelMapper.map(specialty, Specialty.class);

        Specialty specialtyPersisted = specialtyRepository.save(specialtyEntity);

        SpecialtyOutputDto specialtyOutputDto = modelMapper.map(specialtyPersisted, SpecialtyOutputDto.class);

        LOGGER.info("Specialty saved: " + jsonPrinter.toString(specialtyOutputDto));

        return specialtyOutputDto;

    }

    @Override
    public List<SpecialtyOutputDto> listSpecialties() {
        List<SpecialtyOutputDto> specialties = specialtyRepository.findAll()
                .stream()
                .map(specialty -> modelMapper.map(specialty, SpecialtyOutputDto.class))
                .toList();

        LOGGER.info("List of all specialties; {}", jsonPrinter.toString((specialties)));

        return specialties;
    }

    @Override
    public SpecialtyOutputDto findSpecialtyById(Long id) {

        Specialty specialtySearched = specialtyRepository.findById(id).orElse(null);
        SpecialtyOutputDto specialtyFound = null;

        if (specialtySearched != null){
            specialtyFound = modelMapper.map(specialtySearched, SpecialtyOutputDto.class);
            LOGGER.info("Specialty found: {}", jsonPrinter.toString(specialtyFound));
        } else {
            LOGGER.error("The id is not registered on the database.");
        }
        return specialtyFound;

    }

    @Override
    public SpecialtyOutputDto updateSpecialty(SpecialtyUpdateDto specialty) throws ResourceNotFoundException {
        Specialty existingSpecialty = specialtyRepository.findById(specialty.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id " + specialty.getId()));
        existingSpecialty.setName(specialty.getName());
        existingSpecialty.setDescription(specialty.getDescription());
        Specialty updatedSpecialty = specialtyRepository.save(existingSpecialty);
        SpecialtyOutputDto specialtyOutputDto = modelMapper.map(updatedSpecialty, SpecialtyOutputDto.class);
        LOGGER.info("Specialty updated: {}", jsonPrinter.toString(specialtyOutputDto));
        return specialtyOutputDto;
    }

    @Override
    public void deleteSpecialty(Long id) throws ResourceNotFoundException {

        if (specialtyRepository.findById(id).orElse(null) != null){
            specialtyRepository.deleteById(id);
            LOGGER.warn("Specialty with id {} was deleted", id);
        }else {
            LOGGER.error("Couldn't find specialty with id {}", id);
            throw new ResourceNotFoundException("Couldn't find specialty with id " + id);
        }
    }
}

package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.SpecialtyInputDto;
import com.backend.mediConnect.dto.update.SpecialtyUpdateDto;
import com.backend.mediConnect.dto.output.SpecialtyOutputDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ISpecialtyService {
    SpecialtyOutputDto registerSpecialty(SpecialtyInputDto specialty);
    List<SpecialtyOutputDto> listSpecialties();
    SpecialtyOutputDto findSpecialtyById(Long id);
    SpecialtyOutputDto updateSpecialty(SpecialtyUpdateDto specialty) throws ResourceNotFoundException;
    void deleteSpecialty(Long id) throws ResourceNotFoundException;

}

package com.backend.proyecto.service;

import com.backend.proyecto.dto.input.DoctorInputDto;
import com.backend.proyecto.dto.update.DoctorUpdateDto;
import com.backend.proyecto.dto.output.DoctorOutputDto;
import com.backend.proyecto.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IDoctorService {

    DoctorOutputDto registerDoctor(DoctorInputDto doctor);
    List<DoctorOutputDto> listDoctors();
    DoctorOutputDto findDoctorById(Long id);
    DoctorOutputDto updateDoctor(DoctorUpdateDto doctor);
    void deleteDoctor(Long id) throws ResourceNotFoundException;


}

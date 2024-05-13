package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IDoctorService {

    DoctorOutputDto registerDoctor(DoctorInputDto doctor);
    List<DoctorOutputDto> listDoctors();
    DoctorOutputDto findDoctorById(Long id);
    DoctorOutputDto updateDoctor(DoctorUpdateDto doctor);
    void deleteDoctor(Long id) throws ResourceNotFoundException;


}

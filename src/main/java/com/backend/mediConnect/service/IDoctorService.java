package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

public interface IDoctorService {

    DoctorOutputDto registerDoctor(DoctorInputDto doctor, Set<Long> featureIds);
    List<DoctorOutputDto> listDoctors();
    DoctorOutputDto findDoctorById(Long id);
    DoctorOutputDto updateDoctor(DoctorUpdateDto doctor);
    void deleteDoctor(Long id) throws ResourceNotFoundException;

}

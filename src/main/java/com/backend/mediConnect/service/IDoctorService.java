package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.entity.Feature;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IDoctorService {

    DoctorOutputDto registerDoctor(DoctorInputDto doctor, Set<Long> featureIds)throws IOException;

    List<DoctorOutputDto> listDoctors();
    DoctorOutputDto findDoctorById(Long id) throws ResourceNotFoundException;
    DoctorOutputDto updateDoctor(DoctorUpdateDto doctorUpdateDto, Set<Long> featureIds) throws ResourceNotFoundException, IOException;
    void deleteDoctor(Long id) throws ResourceNotFoundException;
    DoctorOutputDto getDoctorFeatures(Long doctorId) throws ResourceNotFoundException;
}

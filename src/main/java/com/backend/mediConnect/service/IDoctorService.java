package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;

public interface IDoctorService {

    DoctorOutputDto registerDoctor(DoctorInputDto doctor) throws IOException;
    List<DoctorOutputDto> listDoctors();
    DoctorOutputDto findDoctorById(Long id) throws ResourceNotFoundException;
    DoctorOutputDto updateDoctor(DoctorUpdateDto doctorUpdateDto) throws ResourceNotFoundException, IOException;
    void deleteDoctor(Long id) throws ResourceNotFoundException;
}

package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.input.PatientInputDto;
import com.backend.mediConnect.dto.output.PatientOutputDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IPatientService {

    PatientOutputDto createPatient(PatientInputDto patientInputDto, UserDto user);

    List<PatientOutputDto> getAllPatients();

    PatientOutputDto getPatientById(Long id) throws ResourceNotFoundException;

    PatientOutputDto updatePatient(Long id, PatientInputDto patientInputDto) throws ResourceNotFoundException;

    void deletePatient(Long id) throws ResourceNotFoundException;
}

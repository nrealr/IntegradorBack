package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.input.PatientInputDto;
import com.backend.mediConnect.dto.output.PatientOutputDto;
import com.backend.mediConnect.entity.Patient;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.PatientRepository;
import com.backend.mediConnect.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientOutputDto createPatient(PatientInputDto patientInputDto, UserDto user) {
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setInsuranceProvider(patientInputDto.getInsuranceProvider());
        // Mapear otros campos según sea necesario

        Patient savedPatient = patientRepository.save(patient);
        return convertToDto(savedPatient);
    }

    @Override
    public List<PatientOutputDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PatientOutputDto getPatientById(Long id) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
        return convertToDto(patient);
    }

    @Override
    public PatientOutputDto updatePatient(Long id, PatientInputDto patientInputDto) throws ResourceNotFoundException {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));

        existingPatient.setInsuranceProvider(patientInputDto.getInsuranceProvider());
        // Mapear otros campos según sea necesario

        Patient updatedPatient = patientRepository.save(existingPatient);
        return convertToDto(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) throws ResourceNotFoundException {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
        patientRepository.delete(existingPatient);
    }

    private PatientOutputDto convertToDto(Patient patient) {
        PatientOutputDto dto = new PatientOutputDto();
        dto.setId(patient.getId());
        dto.setUserId(patient.getUser().getId()); // Asumiendo que tienes un método getId en la clase User
        dto.setInsuranceProvider(patient.getInsuranceProvider());
        // Mapear otros campos según sea necesario
        return dto;
    }
}

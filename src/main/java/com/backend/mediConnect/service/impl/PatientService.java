package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.input.PatientInputDto;
import com.backend.mediConnect.dto.output.PatientOutputDto;
import com.backend.mediConnect.entity.Patient;
import com.backend.mediConnect.entity.Role;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.PatientRepository;
import com.backend.mediConnect.repository.RoleRepository;
import com.backend.mediConnect.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PatientOutputDto createPatient(PatientInputDto patientInputDto, UserDto userDto) {
        User user = convertToUser(userDto);
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setInsuranceProvider(patientInputDto.getInsuranceProvider());

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
        dto.setUserId(patient.getUser().getId());
        dto.setInsuranceProvider(patient.getInsuranceProvider());
        return dto;
    }

    private User convertToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());

        Role role = roleRepository.findByRoleName(userDto.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + userDto.getRole()));
        user.setRole(role);

        return user;
    }
}

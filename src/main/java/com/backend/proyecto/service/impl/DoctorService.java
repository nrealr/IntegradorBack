package com.backend.proyecto.service.impl;

import com.backend.proyecto.dto.input.DoctorInputDto;
import com.backend.proyecto.dto.output.DoctorOutputDto;
import com.backend.proyecto.dto.update.DoctorUpdateDto;
import com.backend.proyecto.entity.Doctor;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import com.backend.proyecto.repository.DoctorRepository;
import com.backend.proyecto.service.IDoctorService;
import com.backend.proyecto.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements IDoctorService {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorOutputDto registerDoctor(DoctorInputDto doctor) {

        LOGGER.info("Doctor information received " + JsonPrinter.toString(doctor));
        Doctor doctorEntity = modelMapper.map(doctor, Doctor.class);

        Doctor doctorPersisted = doctorRepository.save(doctorEntity);

        DoctorOutputDto doctorOutputDto = modelMapper.map(doctorPersisted, DoctorOutputDto.class);

        LOGGER.info("Doctor saved: " + JsonPrinter.toString(doctorOutputDto));

        return doctorOutputDto;

    }

    @Override
    public List<DoctorOutputDto> listDoctors() {
        List<DoctorOutputDto> doctors = doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorOutputDto.class))
                .toList();

        LOGGER.info("List of all doctors; {}", JsonPrinter.toString((doctors)));

        return doctors;
    }

    @Override
    public DoctorOutputDto findDoctorById(Long id) {
        return null;
    }

    @Override
    public DoctorOutputDto updateDoctor(DoctorUpdateDto doctor) {
        return null;
    }

    @Override
    public void deleteDoctor(Long id) throws ResourceNotFoundException {

    }
}

package com.backend.mediConnect.service.impl;
import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.service.IDoctorService;
import com.backend.mediConnect.utils.JsonPrinter;
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

        Doctor doctorSearched = doctorRepository.findById(id).orElse(null);
        DoctorOutputDto doctorFound = null;

        if (doctorSearched != null){
            doctorFound = modelMapper.map(doctorSearched, DoctorOutputDto.class);
            LOGGER.info("Doctor found: {}", JsonPrinter.toString(doctorFound));
        } else {
            LOGGER.error("The id is not registered on the database.");
        }
        return doctorFound;

    }

    @Override
    public DoctorOutputDto updateDoctor(DoctorUpdateDto doctor) {
        return null;
    }

    @Override
    public void deleteDoctor(Long id) throws ResourceNotFoundException {

        if (doctorRepository.findById(id).orElse(null) != null){
            doctorRepository.deleteById(id);
            LOGGER.warn("Doctor with id {} was deleted", id);
        }else {
            LOGGER.error("Couldn't find doctor with id {}", id);
            throw new ResourceNotFoundException("Couldn't find doctor with id " + id);
        }
    }
}

package com.backend.mediConnect.service.impl;
import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.output.SpecialtyOutputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.entity.Specialty;
import com.backend.mediConnect.exceptions.BadRequestException;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.service.IDoctorService;
import com.backend.mediConnect.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorService implements IDoctorService {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;

    private final SpecialtyService specialtyService;
    private final ModelMapper modelMapper;

    public DoctorService(DoctorRepository doctorRepository, SpecialtyService specialtyService, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.specialtyService = specialtyService;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorOutputDto registerDoctor(DoctorInputDto doctor) throws BadRequestException{

        LOGGER.info("Doctor information received " + JsonPrinter.toString(doctor));

        DoctorOutputDto doctorOutputDto;

        SpecialtyOutputDto specialtyOutputDto = specialtyService.findSpecialtyById(doctor.getSpecialtyID());

        String specialtyNotInDB = "Specialty is not found in our database.";

        if(specialtyOutputDto == null){
            LOGGER.error(specialtyNotInDB);
            throw new BadRequestException(specialtyNotInDB);
        } else {

            Specialty specialty = modelMapper.map(specialtyOutputDto, Specialty.class);
            Doctor doctorEntity = modelMapper.map(doctor, Doctor.class);
            doctorEntity.setId(null);
            doctorEntity.setSpecialty(specialty);

            Doctor doctorPersisted = doctorRepository.save(doctorEntity);

            doctorOutputDto = modelMapper.map(doctorPersisted, DoctorOutputDto.class);

            LOGGER.info("Doctor saved: " + JsonPrinter.toString(doctorOutputDto));

        }


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
    public DoctorOutputDto updateDoctor(DoctorUpdateDto doctor) throws ResourceNotFoundException{

        Doctor providedDoctor = modelMapper.map(doctor, Doctor.class);
        Doctor doctorToUpdate = doctorRepository.findById(doctor.getId()).orElse(null);

        DoctorOutputDto doctorOutputDto;

        String docNotFound = "The provided Id does not match any doctor in our database.";
        String specNotFound = "The provided specialty does not exist in our database.";

        if(doctorToUpdate == null){
            LOGGER.info("Cant update: " + docNotFound);
            throw new ResourceNotFoundException(docNotFound);
        } else {

            LOGGER.info("Doctor found: " + JsonPrinter.toString(doctorToUpdate));

            SpecialtyOutputDto specialtyOutputDto = specialtyService.findSpecialtyById(doctor.getSpecialtyID());

            if (specialtyOutputDto == null){
                LOGGER.info("Cant update: " + specNotFound);
                throw new ResourceNotFoundException(specNotFound);

            } else {

                Specialty specialty = modelMapper.map(specialtyOutputDto, Specialty.class);

                doctorToUpdate.setName(providedDoctor.getName());
                doctorToUpdate.setLastname(providedDoctor.getLastname());
                doctorToUpdate.setDescription(providedDoctor.getDescription());
                doctorToUpdate.setImg(providedDoctor.getImg());
                doctorToUpdate.setRut(providedDoctor.getRut());
                doctorToUpdate.setSpecialty(specialty);

                doctorRepository.save(doctorToUpdate);

                doctorOutputDto = modelMapper.map(doctorToUpdate, DoctorOutputDto.class);
            }

        }

        return doctorOutputDto;
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

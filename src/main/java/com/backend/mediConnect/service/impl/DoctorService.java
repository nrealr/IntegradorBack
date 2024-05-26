package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.entity.Specialty;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.repository.SpecialtyRepository;
import com.backend.mediConnect.service.IDoctorService;
import com.backend.mediConnect.utils.JsonPrinter;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

@Service
public class DoctorService implements IDoctorService{

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final JsonPrinter jsonPrinter;


    public DoctorService(DoctorRepository doctorRepository, SpecialtyRepository specialtyRepository, ModelMapper modelMapper, Gson gson, JsonPrinter jsonPrinter) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.jsonPrinter = jsonPrinter;
    }

    @Override
    @Transactional
    public DoctorOutputDto registerDoctor(DoctorInputDto doctorInputDto) throws IOException {
        LOGGER.info("Doctor information received: {}", jsonPrinter.toString(doctorInputDto));

        // Convertir MultipartFile a byte[]
        byte[] imgBytes = doctorInputDto.getImg().getBytes();
        Doctor doctorEntity = new Doctor();
        doctorEntity.setName(doctorInputDto.getName());
        doctorEntity.setLastname(doctorInputDto.getLastname());
        doctorEntity.setRut(doctorInputDto.getRut());
        doctorEntity.setDescription(doctorInputDto.getDescription());
        doctorEntity.setImg(imgBytes);


        Specialty specialty = null;
        try {
            specialty = specialtyRepository.findById(doctorInputDto.getSpecialtyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Specialty not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        doctorEntity.setSpecialty(specialty);

        Doctor doctorPersisted = doctorRepository.save(doctorEntity);
        DoctorOutputDto doctorOutputDto = modelMapper.map(doctorPersisted, DoctorOutputDto.class);

        LOGGER.info("Doctor saved: {}", jsonPrinter.toString(doctorOutputDto));
        return doctorOutputDto;
    }

    @Override
    public List<DoctorOutputDto> listDoctors() {
        List<DoctorOutputDto> doctors = doctorRepository.findAll()
                .stream()
                .map(doctor -> {
                    DoctorOutputDto doctorOutputDto = modelMapper.map(doctor, DoctorOutputDto.class);
                    doctorOutputDto.setSpecialtyId(doctor.getSpecialty().getId()); // Aquí asignamos el ID de la especialidad
                    return doctorOutputDto;
                })
                .toList();
        LOGGER.info("List of all doctors: {}", jsonPrinter.toString(doctors));
        return doctors;
    }

    @Override
    public DoctorOutputDto findDoctorById(Long id) {
        Doctor doctorSearched = doctorRepository.findById(id).orElse(null);
        DoctorOutputDto doctorFound = null;
        if (doctorSearched != null){
            doctorFound = modelMapper.map(doctorSearched, DoctorOutputDto.class);
            LOGGER.info("Doctor found: {}", jsonPrinter.toString(doctorFound));
        } else {
            LOGGER.error("The id is not registered on the database.");
        }
        return doctorFound;
    }


    @Override
    public DoctorOutputDto updateDoctor(DoctorUpdateDto doctorUpdateDto) throws ResourceNotFoundException, IOException {
        Long doctorId = doctorUpdateDto.getId();
        Doctor doctorEntity = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        // Actualiza los campos
        if (doctorUpdateDto.getName() != null) {
            doctorEntity.setName(doctorUpdateDto.getName());
        }
        if (doctorUpdateDto.getLastname() != null) {
            doctorEntity.setLastname(doctorUpdateDto.getLastname());
        }
        if (doctorUpdateDto.getRut() != null) {
            doctorEntity.setRut(doctorUpdateDto.getRut());
        }
        if (doctorUpdateDto.getDescription() != null) {
            doctorEntity.setDescription(doctorUpdateDto.getDescription());
        }
        if (doctorUpdateDto.getImg() != null && !doctorUpdateDto.getImg().isEmpty()) {
            doctorEntity.setImg(doctorUpdateDto.getImg().getBytes());
        }
        if (doctorUpdateDto.getSpecialtyId() != null) {
            Specialty specialty = specialtyRepository.findById(doctorUpdateDto.getSpecialtyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Specialty not found"));
            doctorEntity.setSpecialty(specialty);
        }

        Doctor updatedDoctor = doctorRepository.save(doctorEntity);
        return modelMapper.map(updatedDoctor, DoctorOutputDto.class);
    }


    @Override
    public void deleteDoctor(Long id) throws ResourceNotFoundException {
        Doctor doctorToDelete = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        // Antes de eliminar el doctor, puedes desasociarlo de su especialidad (si lo deseas)
        doctorToDelete.setSpecialty(null);

        doctorRepository.delete(doctorToDelete);
        LOGGER.warn("Doctor with id {} was deleted", id);
    }
}

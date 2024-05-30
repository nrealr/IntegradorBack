package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.entity.Doctor;
import com.backend.mediConnect.entity.Feature;
import com.backend.mediConnect.entity.Specialty;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.DoctorRepository;
import com.backend.mediConnect.repository.FeatureRepository;
import com.backend.mediConnect.repository.SpecialtyRepository;
import com.backend.mediConnect.service.IDoctorService;
import com.backend.mediConnect.utils.JsonPrinter;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.*;
import java.io.IOException;

@Service
public class DoctorService implements IDoctorService {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    private final FeatureRepository featureRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final JsonPrinter jsonPrinter;

    public DoctorService(DoctorRepository doctorRepository, SpecialtyRepository specialtyRepository, ModelMapper modelMapper, FeatureRepository featureRepository, Gson gson, JsonPrinter jsonPrinter) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
        this.modelMapper = modelMapper;
        this.featureRepository = featureRepository;
        this.gson = gson;
        this.jsonPrinter = jsonPrinter;
    }

    @Override
    @Transactional
    public DoctorOutputDto registerDoctor(DoctorInputDto doctorInputDto, Set<Long> featureIds) throws IOException {
        LOGGER.info("Doctor information received: {}", jsonPrinter.toString(doctorInputDto));
        // Doctor doctorEntity = modelMapper.map(doctorInputDto, Doctor.class);


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


        if (featureIds != null && !featureIds.isEmpty()) {
            Set<Feature> features = new HashSet<>(featureRepository.findAllById(featureIds));
            doctorEntity.setFeature(features);
        }

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
    @Transactional
    public DoctorOutputDto updateDoctor(DoctorUpdateDto doctorUpdateDto, Set<Long> featureIds) throws ResourceNotFoundException, IOException {
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
        if (featureIds != null) {
            // Asegúrate de inicializar la colección antes de trabajar con ella
            Hibernate.initialize(doctorEntity.getFeature());
            for (Long featureId : featureIds) {
                Feature feature = featureRepository.findById(featureId)
                        .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + featureId));
                if (!doctorEntity.getFeature().contains(feature)) {
                    doctorEntity.getFeature().add(feature);
                }
            }
        }

        Doctor updatedDoctor = doctorRepository.save(doctorEntity);
        return modelMapper.map(updatedDoctor, DoctorOutputDto.class);
    }

    @Override
    public void deleteDoctor(Long id) throws ResourceNotFoundException {
        Doctor doctorToDelete = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        doctorToDelete.setSpecialty(null);

        doctorRepository.delete(doctorToDelete);
        LOGGER.warn("Doctor with id {} was deleted", id);
    }
    @Override
    public List<Feature> getDoctorFeatures(Long doctorId) throws ResourceNotFoundException {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return new ArrayList<>(doctor.getFeature());
        } else {
            throw new ResourceNotFoundException("Doctor not found");
        }
    }
}

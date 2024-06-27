package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.input.PatientInputDto;
import com.backend.mediConnect.dto.output.PatientOutputDto;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.service.IPatientService;
import com.backend.mediConnect.service.impl.PatientService;
import com.backend.mediConnect.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<PatientOutputDto> createPatient(@RequestBody PatientInputDto patientInputDto,
                                                          @RequestParam Long userId) throws Exception {
        UserDto userDto = userService.findUserById(userId);
        PatientOutputDto createdPatient = patientService.createPatient(patientInputDto, userDto);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<PatientOutputDto>> getAllPatients() {
        List<PatientOutputDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientOutputDto> getPatientById(@PathVariable Long id) throws ResourceNotFoundException {
        PatientOutputDto patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientOutputDto> updatePatient(@PathVariable Long id,
                                                          @RequestBody PatientInputDto patientInputDto) throws ResourceNotFoundException {
        PatientOutputDto updatedPatient = patientService.updatePatient(id, patientInputDto);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) throws ResourceNotFoundException {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
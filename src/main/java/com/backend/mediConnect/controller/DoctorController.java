package com.backend.mediConnect.controller;
import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.exceptions.BadRequestException;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.service.impl.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/register")
    public ResponseEntity<DoctorOutputDto> registerDoctor(@RequestBody @Valid DoctorInputDto doctor) throws BadRequestException {
        return new ResponseEntity<>(doctorService.registerDoctor(doctor), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DoctorOutputDto>> listDoctors(){
        return new ResponseEntity<>(doctorService.listDoctors(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<DoctorOutputDto> findDoctorById(@PathVariable Long id) {
        return new ResponseEntity<>(doctorService.findDoctorById(id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) throws ResourceNotFoundException {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor successfully deleted", HttpStatus.NO_CONTENT);
    }
}

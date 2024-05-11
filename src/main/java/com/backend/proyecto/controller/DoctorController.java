package com.backend.proyecto.controller;
import com.backend.proyecto.dto.input.DoctorInputDto;
import com.backend.proyecto.dto.output.DoctorOutputDto;
import com.backend.proyecto.service.impl.DoctorService;
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
    public ResponseEntity<DoctorOutputDto> registerDoctor(@RequestBody @Valid DoctorInputDto doctor) {
        return new ResponseEntity<>(doctorService.registerDoctor(doctor), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DoctorOutputDto>> listDoctors(){
        return new ResponseEntity<>(doctorService.listDoctors(), HttpStatus.OK);
    }
}

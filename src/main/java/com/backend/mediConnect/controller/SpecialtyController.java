package com.backend.mediConnect.controller;
import com.backend.mediConnect.dto.input.SpecialtyInputDto;
import com.backend.mediConnect.dto.output.FeatureOutputDto;
import com.backend.mediConnect.dto.output.SpecialtyOutputDto;
import com.backend.mediConnect.dto.update.FeatureUpdateDto;
import com.backend.mediConnect.dto.update.SpecialtyUpdateDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.service.impl.SpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/specialties")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping("/register")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<SpecialtyOutputDto> registerSpecialty(@RequestBody @Valid SpecialtyInputDto specialty) {
        return new ResponseEntity<>(specialtyService.registerSpecialty(specialty), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SpecialtyOutputDto>> listSpecialties(){
        return new ResponseEntity<>(specialtyService.listSpecialties(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SpecialtyOutputDto> findSpecialtyById(@PathVariable Long id) {
        return new ResponseEntity<>(specialtyService.findSpecialtyById(id), HttpStatus.OK);
    }


    @PutMapping(value = "/update/{id}")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<SpecialtyOutputDto> updateSpecialty (@ModelAttribute @Validated SpecialtyUpdateDto specialty) throws IOException, ResourceNotFoundException {
        return new ResponseEntity<>(specialtyService.updateSpecialty(specialty), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> deleteSpecialty(@PathVariable Long id) throws ResourceNotFoundException {
        specialtyService.deleteSpecialty(id);
        return new ResponseEntity<>("Specialty successfully deleted", HttpStatus.NO_CONTENT);
    }

}

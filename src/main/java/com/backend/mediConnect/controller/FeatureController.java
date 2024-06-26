package com.backend.mediConnect.controller;
import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.input.FeatureInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.output.FeatureOutputDto;
import com.backend.mediConnect.dto.update.FeatureUpdateDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.service.impl.DoctorService;
import com.backend.mediConnect.service.impl.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/features")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<FeatureOutputDto> addFeature(@ModelAttribute @Validated FeatureInputDto feature) throws IOException {
        return new ResponseEntity<>(featureService.addFeature(feature), HttpStatus.CREATED);
    }

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<List<FeatureOutputDto>> listFeatures(){
        return new ResponseEntity<>(featureService.listFeatures(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<FeatureOutputDto> findFeatureById(@PathVariable Long id) {
        return new ResponseEntity<>(featureService.findFeatureById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<FeatureOutputDto> updateFeature (@ModelAttribute @Validated FeatureUpdateDto feature) throws IOException, ResourceNotFoundException {
        return new ResponseEntity<>(featureService.updateFeature(feature), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> deleteFeature(@PathVariable Long id) throws ResourceNotFoundException {
        featureService.deleteFeature(id);
        return new ResponseEntity<>("Feature successfully deleted", HttpStatus.NO_CONTENT);
    }
}

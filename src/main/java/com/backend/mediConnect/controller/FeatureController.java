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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
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

    @PostMapping("/add")
    public ResponseEntity<FeatureOutputDto> addFeature(@RequestBody @Valid FeatureInputDto feature) {
        return new ResponseEntity<>(featureService.addFeature(feature), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FeatureOutputDto>> listFeatures(){
        return new ResponseEntity<>(featureService.listFeatures(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<FeatureOutputDto> findFeatureById(@PathVariable Long id) {
        return new ResponseEntity<>(featureService.findFeatureById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<FeatureOutputDto> updateFeature (@RequestBody @Valid FeatureUpdateDto feature) {
        return new ResponseEntity<>(featureService.updateFeature(feature), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteFeature(@PathVariable Long id) throws ResourceNotFoundException {
        featureService.deleteFeature(id);
        return new ResponseEntity<>("Feature successfully deleted", HttpStatus.NO_CONTENT);
    }
}

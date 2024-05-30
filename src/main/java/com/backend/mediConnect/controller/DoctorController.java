package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.entity.Feature;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.service.IDoctorService;
import com.backend.mediConnect.service.impl.DoctorService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/register")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<DoctorOutputDto> registerDoctor(
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("rut") String rut,
            @RequestParam("description") String description,
            @RequestParam("img") MultipartFile img,
            @RequestParam("specialtyId") Long specialtyId,
            @RequestParam(required = false) Set<Long> featureIds) throws IOException {
        DoctorInputDto doctorInputDto = new DoctorInputDto();
        doctorInputDto.setName(name);
        doctorInputDto.setLastname(lastname);
        doctorInputDto.setRut(rut);
        doctorInputDto.setDescription(description);
        doctorInputDto.setImg(img);
        doctorInputDto.setSpecialtyId(specialtyId);

        DoctorOutputDto registeredDoctor = doctorService.registerDoctor(doctorInputDto,featureIds);
        return new ResponseEntity<>(registeredDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DoctorOutputDto>> listDoctors() {
        return new ResponseEntity<>(doctorService.listDoctors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorOutputDto> findDoctorById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(doctorService.findDoctorById(id), HttpStatus.OK);
    }

   /* @PutMapping("/update")
    public ResponseEntity<DoctorOutputDto> updateDoctor(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("rut") String rut,
            @RequestParam("description") String description) throws ResourceNotFoundException, IOException {
        DoctorUpdateDto doctorUpdateDto = new DoctorUpdateDto();
        doctorUpdateDto.setId(id);
        doctorUpdateDto.setName(name);
        doctorUpdateDto.setLastname(lastname);
        doctorUpdateDto.setRut(rut);
        doctorUpdateDto.setDescription(description);

        return new ResponseEntity<>(doctorService.updateDoctor(doctorUpdateDto), HttpStatus.OK);
    }
*/
   @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//   @PreAuthorize("hasAuthority('ADMINISTRATOR')")
   public ResponseEntity<DoctorOutputDto> updateDoctor(
           @PathVariable Long id,
           @RequestParam("name") String name,
           @RequestParam("lastname") String lastname,
           @RequestParam("rut") String rut,
           @RequestParam("description") String description,
           @RequestParam("specialtyId") Long specialtyId,
           @RequestParam(value = "img", required = false) MultipartFile img,
           @RequestParam(required = false) Set<Long> featureIds) throws IOException, ResourceNotFoundException {

       DoctorUpdateDto doctorUpdateDto = new DoctorUpdateDto();
       doctorUpdateDto.setId(id);
       doctorUpdateDto.setName(name);
       doctorUpdateDto.setLastname(lastname);
       doctorUpdateDto.setRut(rut);
       doctorUpdateDto.setDescription(description);
       doctorUpdateDto.setSpecialtyId(specialtyId);
       doctorUpdateDto.setImg(img);

       DoctorOutputDto updatedDoctor = doctorService.updateDoctor(doctorUpdateDto, featureIds);
       return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
   }

  /* @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<DoctorOutputDto> updateDoctor(@Valid @RequestBody DoctorUpdateDto doctorUpdateDto)
           throws ResourceNotFoundException, IOException {
       return new ResponseEntity<>(doctorService.updateDoctor(doctorUpdateDto), HttpStatus.OK);
   } */

  /*  @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) throws ResourceNotFoundException {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    */

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("No se puede eliminar el doctor porque está asociado a una especialidad", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/features")
    public ResponseEntity<List<Feature>> getDoctorFeatures(@PathVariable Long id) {
        try {
            List<Feature> features = doctorService.getDoctorFeatures(id);
            return ResponseEntity.ok(features);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

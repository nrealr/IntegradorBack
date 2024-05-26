package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class DoctorUpdateDto {

    @NotNull(message = "ID field cannot be null")
    private Long id;

    @Size(min = 2, message = "Name field must have more than 2 characters")
    private String name;

    @Size(min = 2, message = "Lastname field must have more than 2 characters")
    private String lastname;

    @Size(max = 10, message = "RUT field must have up to 10 characters")
    private String rut;

    @Size(max = 500, message = "Description field must have up to 500 characters")
    private String description;

    private MultipartFile img; // Cambiado de MultipartFile a byte[]

    public @NotNull(message = "Specialty field cannot be null") Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(@NotNull(message = "Specialty field cannot be null") Long specialtyId) {
        this.specialtyId = specialtyId;
    }

    @NotNull(message = "Specialty field cannot be null")
    private Long specialtyId;

    public @NotNull(message = "ID field cannot be null") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "ID field cannot be null") Long id) {
        this.id = id;
    }

    public @Size(min = 2, message = "Name field must have more than 2 characters") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, message = "Name field must have more than 2 characters") String name) {
        this.name = name;
    }

    public @Size(min = 2, message = "Lastname field must have more than 2 characters") String getLastname() {
        return lastname;
    }

    public void setLastname(@Size(min = 2, message = "Lastname field must have more than 2 characters") String lastname) {
        this.lastname = lastname;
    }

    public @Size(max = 10, message = "RUT field must have up to 10 characters") String getRut() {
        return rut;
    }

    public void setRut(@Size(max = 10, message = "RUT field must have up to 10 characters") String rut) {
        this.rut = rut;
    }

    public @Size(max = 500, message = "Description field must have up to 500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description field must have up to 500 characters") String description) {
        this.description = description;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}

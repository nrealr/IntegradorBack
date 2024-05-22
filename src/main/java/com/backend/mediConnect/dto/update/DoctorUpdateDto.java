package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.*;

public class DoctorUpdateDto {

    @NotNull(message = "Id field has to be provided")
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 2, message = "Name field must have more than 2 characters")
    private String name;


    @NotNull(message = "Lastname field cannot be null")
    @NotBlank(message = "Lastname field cannot be blank")
    @Size(min = 2, message = "Lastname field must have more than 2 characters")
    private String lastname;


    @NotNull(message = "RUT field cannot be null")
    @NotBlank(message = "RUT field cannot be blank")
    @Size(max = 10, message = "RUT field must have up to 10 characters")
    private String rut;


    @NotNull(message = "image field cannot be null")
    @NotBlank(message = "image field cannot be blank")
    private String img;

    @NotNull(message = "Description field cannot be null")
    @NotBlank(message = "Description field cannot be blank")
    @Size(max = 500, message = "Description field must have up to 500 characters")
    private String description;

    @NotNull(message = "Specialty field cannot be null")
    private Long specialtyID;

    public DoctorUpdateDto(Long id, String name, String lastname, String rut, String img, String description, Long specialtyID) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = img;
        this.description = description;
        this.specialtyID = specialtyID;
    }

    public DoctorUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSpecialtyID() {
        return specialtyID;
    }

    public void setSpecialtyID(Long specialtyID) {
        this.specialtyID = specialtyID;
    }
}

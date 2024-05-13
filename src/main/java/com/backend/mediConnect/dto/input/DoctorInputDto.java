package com.backend.mediConnect.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorInputDto {

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

    public DoctorInputDto() {
    }

    public DoctorInputDto(String name, String lastname, String rut, String img, String description) {
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = img;
        this.description = description;
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
}

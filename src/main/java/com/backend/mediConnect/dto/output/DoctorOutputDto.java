package com.backend.mediConnect.dto.output;

import org.springframework.web.multipart.MultipartFile;

public class DoctorOutputDto {

    private Long id;
    private String name;
    private String lastname;
    private String rut;
    private byte[] img;
    private String description;
    private Long specialtyId;

    public DoctorOutputDto() {
    }

    public DoctorOutputDto(Long id, String name, String lastname, String rut, byte[] image, String description, Long specialtyId) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = image;
        this.description = description;
        this.specialtyId = specialtyId;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DoctorOutputDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", rut='" + rut + '\'' +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", specialtyid='" + specialtyId + '\'' +
                '}';
    }
}

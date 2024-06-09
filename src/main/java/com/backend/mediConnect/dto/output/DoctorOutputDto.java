package com.backend.mediConnect.dto.output;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Set;

import java.util.List;

public class DoctorOutputDto {

    private Long id;
    private String name;
    private String lastname;
    private String rut;
    private byte[] img;
    private String description;
    private Long specialtyId;
    private Set<FeatureOutputDto> features;
    private Long locationId;

    public DoctorOutputDto() {
    }

    public DoctorOutputDto(Long id, String name, String lastname, String rut, byte[] img, String description, Long specialtyId, Long locationId) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = img;
        this.description = description;
        this.specialtyId = specialtyId;
        this.locationId = locationId;
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

    public Set<FeatureOutputDto> getFeatures() {
        return features;
    }

    public void setFeatures(Set<FeatureOutputDto> features) {
        this.features = features;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "DoctorOutputDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", rut='" + rut + '\'' +
                ", img=" + Arrays.toString(img) +
                ", description='" + description + '\'' +
                ", specialtyId=" + specialtyId +
                ", features=" + features +
                ", locationId=" + locationId +
                '}';
    }
}

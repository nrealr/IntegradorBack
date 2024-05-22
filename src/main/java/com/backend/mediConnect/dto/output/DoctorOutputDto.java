package com.backend.mediConnect.dto.output;

public class DoctorOutputDto {

    private Long id;
    private String name;
    private String lastname;
    private String rut;
    private String img;
    private String description;

    private SpecialtyOutputDto specialty;

    public DoctorOutputDto() {
    }

    public DoctorOutputDto(Long id, String name, String lastname, String rut, String img, String description, SpecialtyOutputDto specialty) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = img;
        this.description = description;
        this.specialty = specialty;
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

    public SpecialtyOutputDto getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyOutputDto specialty) {
        this.specialty = specialty;
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
                ", specialty ='" + specialty + '\'' +
                '}';
    }
}

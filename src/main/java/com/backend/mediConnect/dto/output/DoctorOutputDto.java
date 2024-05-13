package com.backend.mediConnect.dto.output;

public class DoctorOutputDto {

    private Long id;
    private String name;
    private String lastname;
    private String rut;
    private String img;
    private String description;

    public DoctorOutputDto() {
    }

    public DoctorOutputDto(String name, Long id, String lastname, String rut, String image, String description) {
        this.name = name;
        this.id = id;
        this.lastname = lastname;
        this.rut = rut;
        this.img = image;
        this.description = description;
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

    @Override
    public String toString() {
        return "DoctorOutputDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", rut='" + rut + '\'' +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

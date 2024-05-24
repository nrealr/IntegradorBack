package com.backend.mediConnect.entity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="DOCTORS")


public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String lastname;

    @Column(length = 10)
    private String rut;

    @Column(length = 500)
    private String img;

    @Column(length = 500)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "DOCTORSFEATURES",
            joinColumns = @JoinColumn(name = "doctors_id"),
            inverseJoinColumns = @JoinColumn(name = "features_id")
    )
    private Set<Feature> features;

    public Doctor() {
    }

    public Doctor(Long id, String name, String lastname, String rut, String img, String description) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = img;
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

    public Set<Feature> getFeature() {
        return features;
    }

    public void setFeature(Set<Feature> feature) {
        this.features = feature;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", rut='" + rut + '\'' +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

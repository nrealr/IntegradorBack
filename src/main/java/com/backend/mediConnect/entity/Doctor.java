package com.backend.mediConnect.entity;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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

    @Lob
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;

    @Column(length = 500)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @ManyToMany
    @JoinTable(
            name = "DOCTORSFEATURES",
            joinColumns = @JoinColumn(name = "doctors_id"),
            inverseJoinColumns = @JoinColumn(name = "features_id")
    )
    private Set<Feature> features;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    public Doctor() {
    }

    public Doctor(Long id, String name, String lastname, String rut, byte[] img, String description, Specialty specialty, Location location) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.img = img;
        this.description = description;
        this.specialty = specialty;
        this.location = location;
    }

    public Doctor(String name, String lastname, String rut, String description, byte[] img, Specialty specialty, Set<Feature> features, Location location) {
        this.name = name;
        this.lastname = lastname;
        this.rut = rut;
        this.description = description;
        this.img = img;
        this.specialty = specialty;
        this.features = features;
        this.location = location;
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

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> feature) {
        this.features = feature;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", rut='" + rut + '\'' +
                ", img=" + Arrays.toString(img) +
                ", description='" + description + '\'' +
                ", specialty=" + specialty +
                ", location=" + location +
                '}';
    }
}

package com.backend.mediConnect.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name="FEATURES")

public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Lob
    @Column(name = "icon", columnDefinition = "LONGBLOB")
    private byte[] icon;

    @ManyToMany(mappedBy = "features")
    private Set<Doctor> doctors;

    public Feature() {
    }

    public Feature(String name, Long id, byte[] icon) {
        this.name = name;
        this.id = id;
        this.icon = icon;
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

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
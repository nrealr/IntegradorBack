package com.backend.mediConnect.entity;

import com.backend.mediConnect.dto.UserDto;
import jakarta.persistence.*;

@Entity
@Table(name="PATIENTS")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String insuranceProvider;

    public Patient() {
    }

    public Patient(Long id, User user, String insuranceProvider) {
        this.id = id;
        this.user = user;
        this.insuranceProvider = insuranceProvider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }
}

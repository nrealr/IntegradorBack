package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.NotNull;

public class PatientUpdateDto {

    @NotNull(message = "ID field cannot be null")
    private Long id;
    private String insuranceProvider;

    public PatientUpdateDto() {
    }

    public PatientUpdateDto(Long id, String insuranceProvider) {
        this.id = id;
        this.insuranceProvider = insuranceProvider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

}

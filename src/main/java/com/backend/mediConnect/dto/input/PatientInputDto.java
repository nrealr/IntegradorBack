package com.backend.mediConnect.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInputDto {

    @NotNull(message = "User field cannot be null")
    private Long userId;

    @NotNull(message = "Insurance Provider field cannot be null")
    private String insuranceProvider;

    public PatientInputDto() {
    }

    public PatientInputDto(Long userId, String insuranceProvider) {
        this.userId = userId;
        this.insuranceProvider = insuranceProvider;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }
}

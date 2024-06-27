package com.backend.mediConnect.dto.output;

public class PatientOutputDto {

    private Long id;
    private Long userId;
    private String insuranceProvider;

    public PatientOutputDto() {
    }

    public PatientOutputDto(Long id, Long userId, String insuranceProvider) {
        this.id = id;
        this.userId = userId;
        this.insuranceProvider = insuranceProvider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

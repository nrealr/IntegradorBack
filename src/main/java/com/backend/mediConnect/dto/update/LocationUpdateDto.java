package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LocationUpdateDto {
    @NotNull(message = "ID field cannot be null")
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 2, message = "Name field must have more than 2 characters")
    private String name;

    @NotNull(message = "Address field cannot be null")
    @NotBlank(message = "Address field cannot be blank")
    @Size(max = 500, message = "Address field must have up to 500 characters")
    private String address;

    public LocationUpdateDto() {
    }

    public LocationUpdateDto(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.backend.mediConnect.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)

public class SpecialtyInputDto {

    @NotNull(message = "Name field cannot be null")
    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 2, message = "Name field must have more than 2 characters")
    private String name;

    @NotNull(message = "Description field cannot be null")
    @NotBlank(message = "Description field cannot be blank")
    @Size(max = 500, message = "Description field must have up to 500 characters")
    private String description;

    public SpecialtyInputDto() {
    }

    public SpecialtyInputDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

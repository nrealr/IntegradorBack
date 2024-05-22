package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.*;

public class SpecialtyUpdateDto {
    @NotNull(message = "ID field cannot be null")
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 2, message = "Name field must have more than 2 characters")
    private String name;

    @NotNull(message = "Description field cannot be null")
    @NotBlank(message = "Description field cannot be blank")
    @Size(max = 500, message = "Description field must have up to 500 characters")
    private String description;

    public SpecialtyUpdateDto() {
    }

    public SpecialtyUpdateDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

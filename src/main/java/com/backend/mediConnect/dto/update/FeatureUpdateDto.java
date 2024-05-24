package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.*;

public class FeatureUpdateDto {

    @NotNull(message = "The id of the feature to be modified must be provided")
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotBlank(message = "Name field cannot be blank")
    private String name;

    @NotNull(message = "icon field cannot be null")
    @NotBlank(message = "icon field cannot be blank")
    private String icon;

    public FeatureUpdateDto() {
    }

    public FeatureUpdateDto(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

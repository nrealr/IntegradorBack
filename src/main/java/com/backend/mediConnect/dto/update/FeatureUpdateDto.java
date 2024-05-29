package com.backend.mediConnect.dto.update;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class FeatureUpdateDto {

    @NotNull(message = "The id of the feature to be modified must be provided")
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotBlank(message = "Name field cannot be blank")
    private String name;

    private MultipartFile icon;

    public FeatureUpdateDto() {
    }

    public FeatureUpdateDto(Long id, String name, MultipartFile icon) {
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

    public MultipartFile getIcon() {
        return icon;
    }

    public void setIcon(MultipartFile icon) {
        this.icon = icon;
    }
}

package com.backend.mediConnect.dto.output;

public class SpecialtyOutputDto {
    private Long id;
    private String name;
    private String description;

    public SpecialtyOutputDto() {
    }

    public SpecialtyOutputDto(String name, Long id, String description) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return "SpecialtyOutputDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

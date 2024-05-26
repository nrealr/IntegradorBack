package com.backend.mediConnect.dto.input;


import com.backend.mediConnect.entity.Role;

public class UserInputDto {


    private String name;
    private String lastname;
    private String email;
    private String password;
    private String address;
    private Integer phone;

    public UserInputDto() {
    }

    public UserInputDto(String email) {
        this.email = email;
    }

    public UserInputDto(String name, String lastname, String email, String password, String address, Integer phone) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}

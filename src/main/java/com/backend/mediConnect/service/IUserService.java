package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.UserLoginDto;
import com.backend.mediConnect.dto.output.FeatureOutputDto;
import com.backend.mediConnect.entity.RoleName;

import java.util.List;

public interface IUserService {
    public UserDto login(UserLoginDto userLoginDto);
    public UserDto create(UserDto userDto) throws Exception;
    public UserDto findUserById(Long id) throws Exception;
    public UserDto updateUserRole(Long userId, Long roleId) throws Exception;
    public List<UserDto> getAllUsers();
    public UserDto updateUserDetails(Long userId, String name, String lastname, String phone, String address) throws Exception;
    public UserDto getUserByEmail(String email) throws Exception;
    void changePassword(Long userId, String newPassword) throws Exception;

    void resendWelcomeEmail(Long userId) throws Exception;
}

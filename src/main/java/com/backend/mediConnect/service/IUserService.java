package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.UserLoginDto;

public interface IUserService {
    public UserDto login(UserLoginDto userLoginDto);
    public UserDto create(UserDto userDto) throws Exception;
}

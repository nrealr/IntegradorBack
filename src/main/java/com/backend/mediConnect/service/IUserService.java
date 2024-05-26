package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.UserInputDto;
import com.backend.mediConnect.dto.output.UserOutputDto;
import com.backend.mediConnect.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface IUserService extends UserDetailsService {
    UserOutputDto registerUser(UserInputDto userRegister);


}

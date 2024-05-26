package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.config.jwt.JwtProvider;
import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.UserLoginDto;
import com.backend.mediConnect.entity.Role;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.repository.RoleRepository;
import com.backend.mediConnect.repository.UserRepository;
import com.backend.mediConnect.service.IUserService;
import com.backend.mediConnect.service.impl.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;



    @Override
    public UserDto create(UserDto userDto) throws Exception {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByRoleName(userDto.getRole())
                .orElseThrow(()-> new Exception("That role does not exist. Insert it first"));
                user.setRole(role);
        user = userRepository.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = JwtProvider.generateTokenJWT(userLoginDto.getEmail());
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);
        UserDto userDto = userMapper.toUserDto(user);

        userDto.setToken(token);
        return userDto;
    }
}

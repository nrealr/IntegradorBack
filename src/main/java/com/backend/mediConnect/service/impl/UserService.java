package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.UserInputDto;
import com.backend.mediConnect.dto.output.UserOutputDto;
import com.backend.mediConnect.entity.Role;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.repository.UserRepository;
import com.backend.mediConnect.service.IUserService;

import com.backend.mediConnect.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserOutputDto registerUser(UserInputDto userRegister) {
        LOGGER.info("User information received " + JsonPrinter.toString(userRegister));
        User userEntity = modelMapper.map(userRegister, User.class);
        userEntity.setRole(Arrays.asList(new Role("ROLE_USER")));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setEnable(true);

        User userPersisted = userRepository.save(userEntity);

        UserOutputDto userOutputDto = modelMapper.map(userPersisted, UserOutputDto.class);

        LOGGER.info("User saved: " + JsonPrinter.toString(userOutputDto));

        return userOutputDto;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authRoleMap(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> authRoleMap(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

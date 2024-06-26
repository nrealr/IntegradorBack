package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.config.jwt.JwtProvider;
import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.UserLoginDto;
import com.backend.mediConnect.dto.output.FeatureOutputDto;
import com.backend.mediConnect.entity.Role;
import com.backend.mediConnect.entity.RoleName;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.repository.RoleRepository;
import com.backend.mediConnect.repository.UserRepository;
import com.backend.mediConnect.service.IUserService;
import com.backend.mediConnect.service.impl.Mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
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
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());

        // Verificar si es el primer usuario en la base de datos
        boolean isFirstUser = userRepository.count() == 0;

        Role role;
        if (isFirstUser) {
            role = roleRepository.findByRoleName(RoleName.ADMINISTRATOR)
                    .orElseThrow(() -> new Exception("The ADMINISTRATOR role does not exist. Insert a valid role"));
        } else {
            role = roleRepository.findByRoleName(RoleName.REGISTERED)
                    .orElseThrow(() -> new Exception("The REGISTERED role does not exist. Insert a valid role"));
        }

        user.setRole(role);
        user = userRepository.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto findUserById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new Exception("User not found with id: " + id));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUserRole(Long userId, Long roleId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("User not found with id: " + userId));

        Role newRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new Exception("Role not found with id: " + roleId));

        user.setRole(newRole);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUserDetails(Long userId, String name, String lastname) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));

        if (name != null && !name.trim().isEmpty()) {
            user.setName(name);
        }

        if (lastname != null && !lastname.trim().isEmpty()) {
            user.setLastname(lastname);
        }


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

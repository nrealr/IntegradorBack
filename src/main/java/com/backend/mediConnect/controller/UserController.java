package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.UpdateRoleDto;
import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.UserLoginDto;
import com.backend.mediConnect.service.IUserService;
import com.backend.mediConnect.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<String>("User and password are mandatory", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDto>(iUserService.login(userLoginDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto, BindingResult validate)
            throws Exception {

        if (validate.hasErrors()) {
            return new ResponseEntity<String>("Incomplete fields", HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<UserDto>(iUserService.create(userDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = iUserService.findUserById(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserDto> updateUserRole(@PathVariable Long id, @Valid @RequestBody UpdateRoleDto updateRoleDto) {
        try {
            UserDto userDto = iUserService.updateUserRole(id, updateRoleDto.getRoleId());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = iUserService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserDto> updateUserDetails(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = iUserService.updateUserDetails(
                    id,
                    userDto.getName(),
                    userDto.getLastname(),
                    userDto.getPhone(),
                    userDto.getAddress()
            );
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/area/administrator")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> administratorAccess() {
        return new ResponseEntity<>("You are in admin area", HttpStatus.OK);
    }

    @GetMapping("/area/restricted-user")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> restrictedUserAccess() {
        return new ResponseEntity<>("You are in a restricted area", HttpStatus.OK);
    }

    @GetMapping("/area/logged-user")
    public ResponseEntity<?> loggedUser() {
        return new ResponseEntity<>("You are logged, any rol", HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws Exception {
        return iUserService.getUserByEmail(email);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody UserLoginDto userLoginDto) {
        try {
            iUserService.changePassword(id, userLoginDto.getPassword());
            return new ResponseEntity<>("Password changed" , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/resend-welcome-email/{id}")
    public ResponseEntity<?> resendWelcomeEmail(@PathVariable Long id) {
        try {
            iUserService.resendWelcomeEmail(id);
            return new ResponseEntity<>("Welcome email resent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

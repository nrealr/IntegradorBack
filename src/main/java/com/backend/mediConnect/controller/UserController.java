package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.dto.UserLoginDto;
import com.backend.mediConnect.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<String>("User and password are mandatories", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDto>(iUserService.login(userLoginDto), HttpStatus.OK);
    }

    @PostMapping("/create")
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

    @GetMapping("/area/administrator")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> administratorAccess() {
        return new ResponseEntity<>("You are administrator", HttpStatus.OK);
    }

    @GetMapping("/area/restricted-user")
    @PreAuthorize("hasAuthority('RESTRICTED_USER')")
    public ResponseEntity<?> restrictedUserAccess() {
        return new ResponseEntity<>("You are a restricted user", HttpStatus.OK);
    }

    @GetMapping("/area/logged-user")
    public ResponseEntity<?> loggedUser() {
        return new ResponseEntity<>("You are logged, no rol", HttpStatus.OK);
    }
}

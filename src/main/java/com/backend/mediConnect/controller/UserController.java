package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.input.UserInputDto;
import com.backend.mediConnect.dto.output.UserOutputDto;
import com.backend.mediConnect.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<UserOutputDto> registerUser(@RequestBody @Valid UserInputDto user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }
}

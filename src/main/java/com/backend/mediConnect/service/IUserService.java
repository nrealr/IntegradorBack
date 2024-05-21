package com.backend.mediConnect.service;

import com.backend.mediConnect.entity.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<?> saveUser(User user);
    ResponseEntity<?> confirmEmail(String confirmationToken);
}



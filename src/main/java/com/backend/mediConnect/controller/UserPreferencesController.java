package com.backend.mediConnect.controller;

import com.backend.mediConnect.dto.UserPreferencesDto;
import com.backend.mediConnect.service.IUserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-preferences")
public class UserPreferencesController {

    @Autowired
    private IUserPreferencesService userPreferencesService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserPreferencesDto> getUserPreferences(@PathVariable Long userId) {
        try {
            UserPreferencesDto preferences = userPreferencesService.getUserPreferences(userId);
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}/search-history")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserPreferencesDto> updateUserSearchHistory(@PathVariable Long userId, @RequestBody List<String> searchHistory) {
        try {
            UserPreferencesDto preferences = userPreferencesService.updateUserSearchHistory(userId, searchHistory);
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}/favorites")
    @PreAuthorize("hasAuthority('REGISTERED') or hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserPreferencesDto> updateUserFavorites(@PathVariable Long userId, @RequestBody List<Long> favorites) {
        try {
            UserPreferencesDto preferences = userPreferencesService.updateUserFavorites(userId, favorites);
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

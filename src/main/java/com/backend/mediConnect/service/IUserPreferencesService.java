package com.backend.mediConnect.service;
import com.backend.mediConnect.dto.UserPreferencesDto;

import java.util.List;

public interface IUserPreferencesService {
    UserPreferencesDto getUserPreferences(Long userId) throws Exception;
    UserPreferencesDto updateUserSearchHistory(Long userId, List<String> searchHistory) throws Exception;
    UserPreferencesDto updateUserFavorites(Long userId, List<Long> favorites) throws Exception;
}
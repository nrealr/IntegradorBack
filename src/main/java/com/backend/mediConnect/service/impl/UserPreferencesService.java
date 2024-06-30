package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.UserPreferencesDto;
import com.backend.mediConnect.entity.User;
import com.backend.mediConnect.repository.UserRepository;
import com.backend.mediConnect.service.IUserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPreferencesService implements IUserPreferencesService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserPreferencesDto getUserPreferences(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));
        UserPreferencesDto preferences = new UserPreferencesDto();
        preferences.setSearchHistory(user.getSearchHistory());
        preferences.setFavorites(user.getFavorites());
        return preferences;
    }

    @Override
    public UserPreferencesDto updateUserSearchHistory(Long userId, List<String> searchHistory) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));

        List<String> currentSearchHistory = user.getSearchHistory();

        // Ensure that the total size does not exceed 10
        if (searchHistory.size() > 10) {
            searchHistory = searchHistory.subList(searchHistory.size() - 10, searchHistory.size());
        }

        currentSearchHistory.addAll(searchHistory);

        // Keep only the 10 most recent entries
        if (currentSearchHistory.size() > 10) {
            currentSearchHistory = currentSearchHistory.subList(currentSearchHistory.size() - 10, currentSearchHistory.size());
        }

        user.setSearchHistory(currentSearchHistory);
        user = userRepository.save(user);

        UserPreferencesDto preferences = new UserPreferencesDto();
        preferences.setSearchHistory(user.getSearchHistory());
        preferences.setFavorites(user.getFavorites());
        return preferences;
    }

    @Override
    public UserPreferencesDto updateUserFavorites(Long userId, List<Long> favorites) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));
        user.setFavorites(favorites);
        user = userRepository.save(user);
        UserPreferencesDto preferences = new UserPreferencesDto();
        preferences.setSearchHistory(user.getSearchHistory());
        preferences.setFavorites(user.getFavorites());
        return preferences;
    }
}

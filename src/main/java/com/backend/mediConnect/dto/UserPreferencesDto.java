package com.backend.mediConnect.dto;

import java.util.List;

public class UserPreferencesDto {
    private List<String> searchHistory;
    private List<Long> favorites;
    public List<String> getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }

    public List<Long> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }
}
package com.contests.Bean;

import jakarta.validation.constraints.NotBlank;

public class UserRegistrationRequest {

    @NotBlank
    private String userId;

    @NotBlank 
    private String username;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
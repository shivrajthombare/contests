package com.contests.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Document(collection = "users")
public class User {

    @Id
    private String userId;

    private String username;

    @Min(0)
    @Max(100)
    private int score;

    private Set<String> badges;

    public User(String userId, String username, int score, Set<String> badges) {
        this.userId = userId;
        this.username = username;
        this.score = score;
        this.badges = badges;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void updateBadges() {
        badges.clear();
        if (score >= 1 && score < 30) {
            badges.add("Code Ninja");
        } else if (score >= 30 && score < 60) {
            badges.add("Code Champ");
        } else if (score >= 60 && score <= 100) {
            badges.add("Code Master");
        }
    }
}
package com.contests.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contests.Bean.ScoreRequest;
import com.contests.Bean.User;
import com.contests.Bean.UserRegistrationRequest;

@RestController
@RequestMapping("/users")
class UserController {

    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public List<User> getAllUsers() {
        return users.values().stream()
                .sorted(Comparator.comparingInt(User::getScore).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = users.get(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        if (users.containsKey(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User ID already exists.");
        }

        User newUser = new User(request.getUserId(), request.getUsername(), 0, new HashSet<>());
        users.put(request.getUserId(), newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateScore(@PathVariable String userId, @Validated @RequestBody ScoreRequest request) {
        User user = users.get(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        user.setScore(request.getScore());
        user.updateBadges();
        return ResponseEntity.ok("Score updated successfully.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        if (users.remove(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.ok("User deregistered successfully.");
    }
}

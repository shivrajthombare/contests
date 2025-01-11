package com.contests.service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.contests.Bean.UserRegistrationRequest;
import com.contests.entity.User;
import com.contests.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getScore).reversed())
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public boolean registerUser(UserRegistrationRequest request) {
        if (userRepository.existsById(request.getUserId())) {
            return false;
        }
        User newUser = new User(request.getUserId(), request.getUsername(), 0, new HashSet<>());
        userRepository.save(newUser);
        return true;
    }

    public boolean updateScore(String userId, int score) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setScore(score);
            user.updateBadges();
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}

package com.example.bank.service;

import com.example.bank.entity.User;
import com.example.bank.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // -----------------------------
    // Safe login method
    // -----------------------------
    public User login(String email, String password) {
        // Step 1: find user by email
        User user = userRepository.findByEmail(email).orElse(null);

        // Step 2: return null if user not found
        if (user == null) return null;

        // Step 3: null-safe password check
        if (user.getPassword() == null || !user.getPassword().equals(password)) return null;

        // Step 4: login successful
        return user;
    }

    // -----------------------------
    // Get user by ID (profile)
    // -----------------------------
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // -----------------------------
    // Get user by email
    // -----------------------------
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // -----------------------------
    // Change password
    // -----------------------------
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return false;

        // Null-safe check for old password
        if (user.getPassword() == null || !user.getPassword().equals(oldPassword)) return false;

        user.setPassword(newPassword); // TODO: hash in production
        userRepository.save(user);
        return true;
    }

    // -----------------------------
    // Forgot password / reset
    // -----------------------------
    public boolean resetPassword(String email, String tempPassword) {
        User user = getUserByEmail(email);
        if (user == null) return false;

        user.setPassword(tempPassword); // TODO: hash in production
        userRepository.save(user);
        return true;
    }
}

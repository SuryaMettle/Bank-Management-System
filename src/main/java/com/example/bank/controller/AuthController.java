package com.example.bank.controller;

import com.example.bank.dto.*;
import com.example.bank.entity.User;
import com.example.bank.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.bank.entity.Role;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -----------------------------
    // Existing: login
    // -----------------------------
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        User user = authService.login(request.getEmail(), request.getPassword());
        if (user == null) return ResponseEntity.status(401).body("Invalid credentials");

        return ResponseEntity.ok(new AuthResponse(user.getId(), user.getEmail(), user.getRole()));
    } catch (Exception e) {
        e.printStackTrace(); // see the exact error in console
        return ResponseEntity.status(500).body("Internal error: " + e.getMessage());
    }
}


    // -----------------------------
    // New: get profile
    // -----------------------------
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable Long id) {
        User user = authService.getUserById(id);
    if (user == null) return ResponseEntity.notFound().build();

    // Only ADMIN or EMPLOYEE can view
    if (user.getRole() == Role.CUSTOMER) {
        return ResponseEntity.status(403).body(null);
    }

    return ResponseEntity.ok(new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.getStatus()));
}

@PostMapping("/change-password")
public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
    User user = authService.getUserById(request.userId);
    if (user == null) return ResponseEntity.badRequest().body("Invalid user ID");

    // Only ADMIN or EMPLOYEE can change
    if (user.getRole() == Role.CUSTOMER) {
        return ResponseEntity.status(403).body("Customers cannot change password directly");
    }

    boolean success = authService.changePassword(request.userId, request.oldPassword, request.newPassword);
    if (!success) return ResponseEntity.badRequest().body("Invalid old password");
    return ResponseEntity.ok("Password changed successfully");
}

@PostMapping("/forgot-password")
public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    User user = authService.getUserByEmail(request.email);

    if (user == null)
        return ResponseEntity.badRequest().body("Email not found");

    if (user.getRole() == Role.CUSTOMER)
        return ResponseEntity.status(403).body("Customers cannot reset password directly");

    authService.resetPassword(request.email, request.tempPassword);
    return ResponseEntity.ok("Password reset successfully");
}



// -----------------------------
// LoginRequest and AuthResponse remain unchanged
// -----------------------------
public static class LoginRequest {    // <-- replace old LoginRequest
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {    // <-- replace old AuthResponse
        private Long id;
        private String email;
        private com.example.bank.entity.Role role;

        public AuthResponse(Long id, String email, com.example.bank.entity.Role role) {
            this.id = id;
            this.email = email;
            this.role = role;
        }

        public Long getId() { return id; }
        public String getEmail() { return email; }
        public com.example.bank.entity.Role getRole() { return role; }
    }

}

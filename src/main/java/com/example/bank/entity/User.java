package com.example.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String status; // ACTIVE, APPROVED, REJECTED

    // Constructors
    public User() {}
    public User(String email, String password, Role role, String status) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // Getters & setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setStatus(String status) { this.status = status; }
}

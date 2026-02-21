package com.example.bank.dto;

import com.example.bank.entity.Role;

public class UserResponse {
    public Long id;
    public String email;
    public Role role;
    public String status;

    public UserResponse(Long id, String email, Role role, String status) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
    }
}

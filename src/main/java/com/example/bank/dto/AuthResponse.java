package com.example.bank.dto;

import com.example.bank.entity.Role;

public record AuthResponse(Long id, String email, Role role) {}

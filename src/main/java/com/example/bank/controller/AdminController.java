package com.example.bank.controller;

import com.example.bank.dto.UserRequest;
import com.example.bank.dto.UserResponse;
import com.example.bank.entity.Branch;
import com.example.bank.entity.Role;
import com.example.bank.entity.User;
import com.example.bank.repository.BranchRepository;
import com.example.bank.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BranchRepository branchRepo;
    private final AdminService adminService;

    public AdminController(BranchRepository branchRepo, AdminService adminService) {
        this.branchRepo = branchRepo;
        this.adminService = adminService;
    }

    @GetMapping("/branches")
    public List<Branch> getBranches() {
        return branchRepo.findAll();
    }

    @PostMapping("/branches")
    public Branch createBranch(@RequestBody Branch branch) {
        return branchRepo.save(branch);
    }
    // -----------------------------
    // New: create user dynamically
    // -----------------------------
    @PostMapping("/users")
    public UserResponse createUser(@RequestBody UserRequest request) {
        User user = new User();
        user.setEmail(request.email);
        user.setPassword(request.password);
        user.setRole(Role.valueOf(request.role.toUpperCase()));
        user.setStatus("ACTIVE");

        user = adminService.createUser(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.getStatus());
    }
    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

}
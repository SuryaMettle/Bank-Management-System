package com.example.bank.service;

import com.example.bank.dto.UserResponse;
import com.example.bank.entity.Branch;
import com.example.bank.entity.Role;
import com.example.bank.entity.User;
import com.example.bank.repository.BranchRepository;
import com.example.bank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    public AdminService(UserRepository userRepository, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
    }

    // Create employee
    public User createEmployee(User user) {
        user.setRole(Role.EMPLOYEE);
        user.setStatus("ACTIVE");
        return userRepository.save(user);
    }

    // Create branch
    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    // List all customers
    public List<User> getAllCustomers() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .collect(Collectors.toList());
    }
    // New method: create user dynamically
    // -----------------------------
    public User createUser(User user) {
        // Check for duplicate email
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new RuntimeException("Email already exists!");
        }
        return userRepository.save(user);
    }
    // Approve customer
    public String approveCustomer(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found";
        if (user.getRole() == Role.CUSTOMER) {
            user.setStatus("APPROVED");
            userRepository.save(user);
            return "Customer approved successfully!";
        } else {
            return "User is not a customer!";
        }
    }
    public List<UserResponse> getAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(user -> new UserResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getRole(),
                    user.getStatus()
            ))
            .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getRole(),
            user.getStatus()
        );
    }


    // Reject customer
    public String rejectCustomer(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found";
        if (user.getRole() == Role.CUSTOMER) {
            user.setStatus("REJECTED");
            userRepository.save(user);
            return "Customer rejected successfully!";
        } else {
            return "User is not a customer!";
        }
    }
}

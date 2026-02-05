package com.example.bank.service;

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

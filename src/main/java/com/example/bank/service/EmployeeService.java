package com.example.bank.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Role;
import com.example.bank.entity.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;

@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public EmployeeService(UserRepository userRepository,
                           AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    // ===========================
    // Create Customer
    // ===========================
    public User createCustomer(User user) {
        user.setRole(Role.CUSTOMER);  // Ensure Role enum has CUSTOMER
        user.setStatus("PENDING");    // New customer initially PENDING approval
        return userRepository.save(user);
    }

    // ===========================
    // Approve Customer
    // ===========================
    public String approveCustomer(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found";

        if (user.getRole() == Role.CUSTOMER) {
            user.setStatus("APPROVED");
            userRepository.save(user);
            return "Customer approved!";
        } else {
            return "User is not a customer!";
        }
    }

    // ===========================
    // Reject Customer
    // ===========================
    public String rejectCustomer(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found";

        if (user.getRole() == Role.CUSTOMER) {
            user.setStatus("REJECTED");
            userRepository.save(user);
            return "Customer rejected!";
        } else {
            return "User is not a customer!";
        }
    }

    // ===========================
    // Open Bank Account
    // ===========================
    public Account openAccount(Account account) {
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");
        return accountRepository.save(account);
    }
}

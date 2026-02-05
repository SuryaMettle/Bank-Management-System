package com.example.bank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.dto.AccountRequest;
import com.example.bank.entity.Account;
import com.example.bank.entity.Branch;
import com.example.bank.entity.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.BranchRepository;
import com.example.bank.repository.UserRepository;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;
    private final BranchRepository branchRepo;

    public EmployeeController(AccountRepository accountRepo,
                              UserRepository userRepo,
                              BranchRepository branchRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return accountRepo.findAll();
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody AccountRequest request) {

        User customer = userRepo.findById(request.customerId).orElse(null);
        Branch branch = branchRepo.findById(request.branchId).orElse(null);

        Account account = new Account();
        account.setAccountNumber(request.accountNumber);
        account.setAccountType(request.accountType);
        account.setBalance(request.balance);
        account.setStatus(request.status);
        account.setCustomer(customer);
        account.setBranch(branch);

        return accountRepo.save(account);
    }
}

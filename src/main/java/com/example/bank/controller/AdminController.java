package com.example.bank.controller;

import com.example.bank.entity.Branch;
import com.example.bank.repository.BranchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BranchRepository branchRepo;

    public AdminController(BranchRepository branchRepo) {
        this.branchRepo = branchRepo;
    }

    @GetMapping("/branches")
    public List<Branch> getBranches() {
        return branchRepo.findAll();
    }

    @PostMapping("/branches")
    public Branch createBranch(@RequestBody Branch branch) {
        return branchRepo.save(branch);
    }
}

package com.example.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branchName;
    private String location;

    // Constructors
    public Branch() {}
    public Branch(String branchName, String location) {
        this.branchName = branchName;
        this.location = location;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getBranchName() { return branchName; }
    public String getLocation() { return location; }

    public void setId(Long id) { this.id = id; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public void setLocation(String location) { this.location = location; }
}

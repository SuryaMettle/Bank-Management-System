package com.example.bank.dto;

import java.math.BigDecimal;

public class AccountRequest {
    public String accountNumber;
    public String accountType;
    public BigDecimal balance;
    public String status;
    public Long customerId;
    public Long branchId;
}


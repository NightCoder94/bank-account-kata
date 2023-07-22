package com.capco.external.kata;

import com.capco.external.kata.BankOperation;

import java.math.BigDecimal;

public record Deposit(BigDecimal amount) implements BankOperation {
    @Override
    public BigDecimal apply(BigDecimal balance) {
        return balance.add(amount);
    }
}

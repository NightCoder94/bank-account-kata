package com.capco.external.kata.operation;

import com.capco.external.kata.operation.BankOperation;

import java.math.BigDecimal;

public record Deposit(BigDecimal amount) implements BankOperation {
    @Override
    public BigDecimal apply(BigDecimal balance) {
        return balance.add(amount);
    }
}

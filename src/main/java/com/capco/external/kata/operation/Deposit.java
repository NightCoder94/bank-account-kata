package com.capco.external.kata.operation;

import com.capco.external.kata.exception.InvalidAmountException;
import com.capco.external.kata.operation.BankOperation;

import java.math.BigDecimal;

public record Deposit(BigDecimal amount) implements BankOperation {
    @Override
    public BigDecimal apply(BigDecimal balance) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("The deposit amount must be positive.");
        }
        return balance.add(amount);
    }
}

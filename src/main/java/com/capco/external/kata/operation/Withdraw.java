package com.capco.external.kata.operation;

import com.capco.external.kata.exception.InsufficientFundsException;
import com.capco.external.kata.exception.InvalidAmountException;

import java.math.BigDecimal;

public record Withdraw(BigDecimal amount) implements BankOperation {
    @Override
    public BigDecimal apply(BigDecimal balance) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("The withdraw amount must be positive.");
        }
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException("The withdrawal amount is greater than the available balance.");
        }
        return balance.subtract(amount);
    }
}

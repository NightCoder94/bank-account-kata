package com.capco.external.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount();
    }

    // Deposit Scenarios

    @Test
    public void testDepositPositiveAmountOnEmptyAccount() {
        bankAccount.deposit(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactionHistory().size());
        Transaction expectedTransaction = new Transaction(TransactionType.DEPOSIT, LocalDateTime.now(), new BigDecimal("100"), new BigDecimal("100"));
        assertEquals(expectedTransaction, bankAccount.getTransactionHistory().get(0));
    }
}

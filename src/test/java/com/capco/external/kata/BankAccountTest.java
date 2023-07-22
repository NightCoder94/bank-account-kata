package com.capco.external.kata;

import com.capco.external.kata.transaction.Transaction;
import com.capco.external.kata.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount(new BankClient("TOTI", "TOTO"));
    }

    // Deposit Scenarios

    @Test
    public void test_deposit_positive_amount_on_empty_account_should_update_balance_and_transaction_history() {
        bankAccount.deposit(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactionHistory().getTransactions().size());
        
        Transaction addedTransaction = bankAccount.getTransactionHistory().getTransactions().get(0);
        Transaction expectedTransaction = new Transaction(UUID.randomUUID(), TransactionType.DEPOSIT, LocalDateTime.now(), new BigDecimal("100"), new BigDecimal("100"));
        assertEquals(TransactionType.DEPOSIT, addedTransaction.transactionType());
        assertEquals(expectedTransaction.amount(), addedTransaction.amount());
        assertEquals(expectedTransaction.balance(), addedTransaction.balance());

        // Truncate seconds to compare the transactions (delta in creation time)
        assertEquals(expectedTransaction.date().truncatedTo(ChronoUnit.SECONDS), addedTransaction.date().truncatedTo(ChronoUnit.SECONDS));
    }
}

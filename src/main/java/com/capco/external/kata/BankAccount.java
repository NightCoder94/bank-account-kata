package com.capco.external.kata;

import com.capco.external.kata.operation.BankOperation;
import com.capco.external.kata.operation.Deposit;
import com.capco.external.kata.transaction.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccount {

    private final BankClient client;
    private final TransactionHistory transactionHistory;
    private BigDecimal balance;

    public BankAccount(BankClient bankClient) {
        this.transactionHistory = new TransactionHistory();
        this.client = bankClient;
        this.balance = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        BankOperation operation = new Deposit(amount);
        balance = operation.apply(balance);
        Transaction depositTransaction = new Transaction(UUID.randomUUID(), TransactionType.DEPOSIT, LocalDateTime.now(), amount, balance);
        transactionHistory.addTransaction(depositTransaction);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

}

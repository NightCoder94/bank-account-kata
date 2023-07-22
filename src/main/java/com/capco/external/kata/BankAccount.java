package com.capco.external.kata;

import com.capco.external.kata.exception.InvalidAmountException;
import com.capco.external.kata.operation.BankOperation;
import com.capco.external.kata.operation.Deposit;
import com.capco.external.kata.operation.Withdraw;
import com.capco.external.kata.transaction.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("The deposit amount must be positive.");
        }

        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            BankOperation operation = new Deposit(amount);
            balance = operation.apply(balance);
            Transaction depositTransaction = new Transaction(TransactionType.DEPOSIT, LocalDateTime.now(), amount, balance);
            transactionHistory.addTransaction(depositTransaction);
        }
    }

    public void withdraw(BigDecimal amount) {
        BankOperation operation = new Withdraw(amount);
        balance = operation.apply(balance);
        Transaction withdrawTransaction = new Transaction(TransactionType.WITHDRAW, LocalDateTime.now(), amount, balance);
        transactionHistory.addTransaction(withdrawTransaction);
    }

    public BankClient getClient() {
        return client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory.getTransactions();
    }

}

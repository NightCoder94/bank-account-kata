package com.capco.external.kata;

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

    public BankClient getClient() {
        return client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactionHistory.getTransactions();
    }

    public void deposit(BigDecimal amount) {
        performOperation(amount, TransactionType.DEPOSIT);
    }

    public void withdraw(BigDecimal amount) {
        performOperation(amount, TransactionType.WITHDRAW);
    }

    private void performOperation(BigDecimal amount, TransactionType transactionType) {
        BankOperation operation;
        if (TransactionType.DEPOSIT.equals(transactionType)){
            operation = new Deposit(amount);
        } else {
            operation = new Withdraw(amount);
        }
        balance = operation.apply(balance);
        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            Transaction withdrawTransaction = new Transaction(transactionType, LocalDateTime.now(), amount, balance);
            transactionHistory.addTransaction(withdrawTransaction);
        }
    }

}

package com.capco.external.kata.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionHistory {

    private final List<Transaction> transactions;

    public TransactionHistory() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

}

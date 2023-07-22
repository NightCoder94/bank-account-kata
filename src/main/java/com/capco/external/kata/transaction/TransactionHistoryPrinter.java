package com.capco.external.kata.transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionHistoryPrinter {

    private static final String STATEMENT_HEADER = "Transaction List for: %s";
    private static final String STATEMENT_FORMAT = "%s\t%s\t\t%s\t\t%s";

    public void printHistory(String clientName, List<Transaction> transactions) {
        System.out.println(String.format(STATEMENT_HEADER, clientName));
        System.out.println("Date\t\tOperation\tAmount\t\tBalance");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transaction transaction : transactions) {
            System.out.println(String.format(STATEMENT_FORMAT, formatter.format(transaction.date()), transaction.transactionType(), transaction.amount(), transaction.balance()));
        }
    }
}

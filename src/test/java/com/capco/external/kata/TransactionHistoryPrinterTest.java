package com.capco.external.kata;

import com.capco.external.kata.transaction.Transaction;
import com.capco.external.kata.transaction.TransactionType;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Transaction Histrory Printer Test Case")
public class TransactionHistoryPrinterTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private TransactionHistoryPrinter transactionHistoryPrinter;

    @BeforeEach
    public void setUp() {
        transactionHistoryPrinter = new TransactionHistoryPrinter();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testPrintStatement() {
        List<Transaction> transactionHistory = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        transactionHistory.add(new Transaction(TransactionType.DEPOSIT, date, new BigDecimal("100"), new BigDecimal("100")));
        transactionHistory.add(new Transaction(TransactionType.WITHDRAW, date, new BigDecimal("30"), new BigDecimal("70")));

        transactionHistoryPrinter.printHistory(transactionHistory);

        String expectedOutput = """
                                    Statement:
                                    Date\t\t\tOperation\tAmount\t\tBalance
                                    %s\tDEPOSIT\t\t100\t\t100
                                    %s\tWITHDRAW\t30\t\t70
                                """.formatted(date, date);

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}

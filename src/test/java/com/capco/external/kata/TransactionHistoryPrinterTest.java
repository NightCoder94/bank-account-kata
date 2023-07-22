package com.capco.external.kata;

import com.capco.external.kata.transaction.Transaction;
import com.capco.external.kata.transaction.TransactionHistoryPrinter;
import com.capco.external.kata.transaction.TransactionType;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Transaction History Printer Test Case")
public class TransactionHistoryPrinterTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private TransactionHistoryPrinter transactionHistoryPrinter;

    @BeforeEach
    public void setUp() {
        transactionHistoryPrinter = new TransactionHistoryPrinter();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @Order(0)
    @DisplayName("Print all transactions history")
    void test_print_all_transaction_history() {
        List<Transaction> transactionHistory = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        transactionHistory.add(new Transaction(TransactionType.DEPOSIT, date, new BigDecimal("100"), new BigDecimal("100")));
        transactionHistory.add(new Transaction(TransactionType.WITHDRAW, date, new BigDecimal("30"), new BigDecimal("70")));

        transactionHistoryPrinter.printHistory("Dupond", transactionHistory);

        String expectedOutput = """
                                Transaction List for: Dupond
                                Date\t\tOperation\tAmount\t\tBalance
                                %s\tDEPOSIT\t\t100\t\t100
                                %s\tWITHDRAW\t\t30\t\t70
                                """.formatted(formatter.format(date), formatter.format(date));

        assertEquals(expectedOutput.replaceAll("(\r\n|\n)", ""), outputStreamCaptor.toString().replaceAll("(\r\n|\n)", ""));
    }
}

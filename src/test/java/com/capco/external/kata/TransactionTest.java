package com.capco.external.kata;

import com.capco.external.kata.transaction.Transaction;
import com.capco.external.kata.transaction.TransactionType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Bank Account Test Case")
public class TransactionTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(new BankClient("TOTI", "TOTO"));
    }

    @Test
    @Order(0)
    @DisplayName("Transaction history")
    public void test_transaction_history_should_return_exact_list_of_transaction() {
        bankAccount.deposit(new BigDecimal("100"));
        bankAccount.withdraw(new BigDecimal("30"));
        bankAccount.deposit(new BigDecimal("50"));
        bankAccount.withdraw(new BigDecimal("20"));
        List<Transaction> transactionHistory = bankAccount.getTransactions();
        assertEquals(4, transactionHistory.size());
        assertEquals(TransactionType.DEPOSIT, transactionHistory.get(0).transactionType());
        assertEquals(TransactionType.WITHDRAW, transactionHistory.get(1).transactionType());
        assertEquals(TransactionType.DEPOSIT, transactionHistory.get(2).transactionType());
        assertEquals(TransactionType.WITHDRAW, transactionHistory.get(3).transactionType());
    }

    @Test
    @Order(1)
    @DisplayName("Balance should be accurate when concurrent transactions happen")
    public void test_concurrent_transactions_should_balance_updated() throws InterruptedException {

        AtomicInteger counter = new AtomicInteger(0);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                bankAccount.deposit(new BigDecimal("100"));
                counter.addAndGet(100);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                bankAccount.withdraw(new BigDecimal("5"));
                counter.addAndGet(-5);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(new BigDecimal(counter.get()), bankAccount.getBalance());
        assertEquals(1500, bankAccount.getTransactions().size());
    }
}

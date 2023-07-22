package com.capco.external.kata;

import com.capco.external.kata.exception.InsufficientFundsException;
import com.capco.external.kata.exception.InvalidAmountException;
import com.capco.external.kata.transaction.Transaction;
import com.capco.external.kata.transaction.TransactionType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Bank Account Test Case")
public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(new BankClient("123456789", "TOTO"));
    }

    // Deposit Scenarios

    @Test
    @DisplayName("New bank account should have zero balance")
    @Order(0)
    void test_create_new_account_should_have_zero_balance() {
        assertEquals(BigDecimal.ZERO, bankAccount.getBalance());
    }

    @Test
    @Order(1)
    @DisplayName("Deposit positive amount on empty account")
    void test_deposit_positive_amount_on_empty_account_should_update_balance_and_transaction_history() {
        bankAccount.deposit(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        Transaction addedTransaction = bankAccount.getTransactions().get(0);
        Transaction expectedTransaction = new Transaction(TransactionType.DEPOSIT, LocalDateTime.now(), new BigDecimal("100"), new BigDecimal("100"));
        assertEquals(TransactionType.DEPOSIT, addedTransaction.transactionType());
        assertEquals(expectedTransaction.amount(), addedTransaction.amount());
        assertEquals(expectedTransaction.balance(), addedTransaction.balance());

        // Truncate seconds to compare the transactions (delta in creation time)
        assertEquals(expectedTransaction.date().truncatedTo(ChronoUnit.MINUTES), addedTransaction.date().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    @Order(2)
    @DisplayName("Deposit positive amount on account with positive balance")
    void test_deposit_positive_amount_on_account_with_positive_balance_should_update_balance_and_transaction_history() {
        bankAccount.deposit(new BigDecimal("500"));
        bankAccount.deposit(new BigDecimal("300"));
        assertEquals(new BigDecimal("800"), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());

        Transaction expectedTransaction1 = new Transaction(TransactionType.DEPOSIT, LocalDateTime.now(), new BigDecimal("500"), new BigDecimal("500"));
        Transaction addedTransaction1 = bankAccount.getTransactions().get(0);
        assertEquals(TransactionType.DEPOSIT, addedTransaction1.transactionType());
        assertEquals(expectedTransaction1.amount(), addedTransaction1.amount());
        assertEquals(expectedTransaction1.balance(), addedTransaction1.balance());

        Transaction expectedTransaction2 = new Transaction(TransactionType.DEPOSIT, LocalDateTime.now(), new BigDecimal("300"), new BigDecimal("800"));
        Transaction addedTransaction2 = bankAccount.getTransactions().get(1);
        assertEquals(TransactionType.DEPOSIT, addedTransaction2.transactionType());
        assertEquals(expectedTransaction2.amount(), addedTransaction2.amount());
        assertEquals(expectedTransaction2.balance(), addedTransaction2.balance());
    }

    @Test
    @Order(3)
    @DisplayName("Deposit negative amount")
    void test_deposit_negative_amount_should_throw_exception() {
        assertThrows(InvalidAmountException.class, () -> bankAccount.deposit(new BigDecimal("-50")));
        assertEquals(new BigDecimal("0"), bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());
    }

    @Test
    @Order(4)
    @DisplayName("Deposit zero amount")
    void test_deposit_zero_amount_should_not_modify_balance() {
        bankAccount.deposit(new BigDecimal("0"));
        assertEquals(new BigDecimal("0"), bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());
    }

    // Withdraw Scenarios

    @Test
    @Order(5)
    @DisplayName("Withdraw positive amount with sufficient balance")
    void test_withdraw_positive_amount_with_sufficient_balance_should_update_balance_and_transaction_history() {
        bankAccount.deposit(new BigDecimal("1000"));
        bankAccount.withdraw(new BigDecimal("300"));
        assertEquals(new BigDecimal("700"), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());

        Transaction expectedTransaction1 = new Transaction(TransactionType.DEPOSIT, LocalDateTime.now(), new BigDecimal("1000"), new BigDecimal("1000"));
        Transaction addedTransaction1 = bankAccount.getTransactions().get(0);
        assertEquals(TransactionType.DEPOSIT, addedTransaction1.transactionType());
        assertEquals(expectedTransaction1.amount(), addedTransaction1.amount());
        assertEquals(expectedTransaction1.balance(), addedTransaction1.balance());

        Transaction expectedTransaction2 = new Transaction(TransactionType.WITHDRAW, LocalDateTime.now(), new BigDecimal("300"), new BigDecimal("700"));
        Transaction addedTransaction2 = bankAccount.getTransactions().get(1);
        assertEquals(TransactionType.WITHDRAW, addedTransaction2.transactionType());
        assertEquals(expectedTransaction2.amount(), addedTransaction2.amount());
        assertEquals(expectedTransaction2.balance(), addedTransaction2.balance());
    }

    @Test
    @Order(6)
    @DisplayName("Withdraw negative amount")
    void test_withdraw_negative_amount_should_throw_exception() {
        bankAccount.deposit(new BigDecimal("500"));
        assertThrows(InvalidAmountException.class, () -> bankAccount.withdraw(new BigDecimal("-50")));
        assertEquals(new BigDecimal("500"), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    @Order(7)
    @DisplayName("Withdraw with insufficient funds")
    void test_withdraw_amount_greater_than_balance_should_throw_exception() {
        bankAccount.deposit(new BigDecimal("200"));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(new BigDecimal("300")));
        assertEquals(new BigDecimal("200"), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    @Order(7)
    @DisplayName("Withdraw zero amount")
    void test_withdraw_zero_amount_should_tot_modify_anything() {
        bankAccount.deposit(new BigDecimal("100"));
        bankAccount.withdraw(new BigDecimal("0"));
        assertEquals(new BigDecimal("100"), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

}

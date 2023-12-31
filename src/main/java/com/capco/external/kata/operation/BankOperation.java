package com.capco.external.kata.operation;

import java.math.BigDecimal;

public sealed interface BankOperation permits Deposit, Withdraw {
    BigDecimal apply(BigDecimal balance);
}

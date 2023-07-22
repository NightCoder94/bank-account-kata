package com.capco.external.kata;

import java.math.BigDecimal;

public sealed interface BankOperation permits Deposit {
    BigDecimal apply(BigDecimal balance);
}

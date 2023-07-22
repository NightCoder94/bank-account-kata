package com.capco.external.kata;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(UUID transactionId, TransactionType transactionType, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
}

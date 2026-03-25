package com.fraud.domain.model;

import java.math.BigDecimal;

public record Transaction(BigDecimal amount, String transactionCountry, String userCountry) {
}

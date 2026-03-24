package com.fraud.infrastructure.controller.dto;

import java.math.BigDecimal;

public record FraudEvaluationRequest(BigDecimal amount, String transactionCountry, String userCountry) {
}

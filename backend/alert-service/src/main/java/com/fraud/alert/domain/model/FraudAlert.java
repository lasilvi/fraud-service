package com.fraud.alert.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FraudAlert(Long id, String userId, BigDecimal amount, String riskLevel, List<String> reasons, LocalDateTime timestamp) {
}

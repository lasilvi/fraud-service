package com.fraud.alert.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record FraudEvent(
        String userId,
        BigDecimal amount,
        String riskLevel,
        List<String> reasons
) {
}

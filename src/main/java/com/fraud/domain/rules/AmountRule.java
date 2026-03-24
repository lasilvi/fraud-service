package com.fraud.domain.rules;

import com.fraud.domain.model.FraudReason;
import java.math.BigDecimal;
import java.util.Optional;

public class AmountRule implements FraudRule {

    private final BigDecimal threshold;

    public AmountRule(BigDecimal threshold) {
        this.threshold = threshold;
    }

    @Override
    public Optional<FraudReason> evaluate(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        if (amount.compareTo(threshold) > 0) {
            return Optional.of(FraudReason.HIGH_AMOUNT);
        }

        return Optional.empty();
    }
}

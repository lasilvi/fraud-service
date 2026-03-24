package com.fraud.domain.rules;

import com.fraud.domain.model.FraudReason;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class AmountRule implements FraudRule {

    private final BigDecimal threshold;

    public AmountRule(BigDecimal threshold) {
        this.threshold = Objects.requireNonNull(threshold, "Threshold cannot be null");
    }

    @Override
    public Optional<FraudReason> evaluate(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (isNegative(amount)) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        if (isAboveThreshold(amount)) {
            return Optional.of(FraudReason.HIGH_AMOUNT);
        }

        return Optional.empty();
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.signum() < 0;
    }

    private boolean isAboveThreshold(BigDecimal amount) {
        return amount.compareTo(threshold) > 0;
    }
}

package com.fraud.domain.rules;

import com.fraud.domain.model.FraudReason;
import java.math.BigDecimal;
import java.util.Optional;

public class AmountRule implements FraudRule {

    public AmountRule(BigDecimal threshold) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<FraudReason> evaluate(BigDecimal amount) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

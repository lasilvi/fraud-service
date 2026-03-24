package com.fraud.domain.rules;

import com.fraud.domain.model.FraudReason;
import java.math.BigDecimal;
import java.util.Optional;

public interface FraudRule {

    Optional<FraudReason> evaluate(BigDecimal amount);
}

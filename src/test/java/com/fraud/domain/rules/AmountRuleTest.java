package com.fraud.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fraud.domain.model.FraudReason;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AmountRuleTest {

    private static final BigDecimal THRESHOLD = new BigDecimal("15000");
    private final AmountRule rule = new AmountRule(THRESHOLD);

    @Test
    void shouldReturnHighAmountWhenAmountIsGreaterThanThreshold() {
        Optional<FraudReason> result = rule.evaluate(new BigDecimal("15000.01"));

        assertEquals(Optional.of(FraudReason.HIGH_AMOUNT), result);
    }

    @Test
    void shouldReturnNoFraudWhenAmountIsEqualToThreshold() {
        Optional<FraudReason> result = rule.evaluate(new BigDecimal("15000"));

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnNoFraudWhenAmountIsLowerThanThreshold() {
        Optional<FraudReason> result = rule.evaluate(new BigDecimal("14999.99"));

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> rule.evaluate(new BigDecimal("-0.01")));
    }
}

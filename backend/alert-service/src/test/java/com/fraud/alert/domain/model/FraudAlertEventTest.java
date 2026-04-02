package com.fraud.alert.domain.model;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FraudAlertEventTest {

    @Test
    void shouldCreateFraudAlertEventWithValidData() {
        // Arrange
        String userId = "user123";
        BigDecimal amount = new BigDecimal("25000.00");
        String riskLevel = "HIGH";
        List<String> reasons = List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION");

        // Act
        FraudAlertEvent event = new FraudAlertEvent(userId, amount, riskLevel, reasons);

        // Assert
        assertNotNull(event);
        assertEquals(userId, event.userId());
        assertEquals(amount, event.amount());
        assertEquals(riskLevel, event.riskLevel());
        assertEquals(reasons, event.reasons());
    }

    @Test
    void shouldCreateFraudAlertEventWithMediumRisk() {
        // Arrange
        String userId = "user456";
        BigDecimal amount = new BigDecimal("16000.00");
        String riskLevel = "MEDIUM";
        List<String> reasons = List.of("HIGH_AMOUNT");

        // Act
        FraudAlertEvent event = new FraudAlertEvent(userId, amount, riskLevel, reasons);

        // Assert
        assertNotNull(event);
        assertEquals("MEDIUM", event.riskLevel());
        assertEquals(1, event.reasons().size());
    }

    @Test
    void shouldCreateFraudAlertEventWithLowRisk() {
        // Arrange
        String userId = "user789";
        BigDecimal amount = new BigDecimal("100.00");
        String riskLevel = "LOW";
        List<String> reasons = List.of();

        // Act
        FraudAlertEvent event = new FraudAlertEvent(userId, amount, riskLevel, reasons);

        // Assert
        assertNotNull(event);
        assertEquals("LOW", event.riskLevel());
        assertTrue(event.reasons().isEmpty());
    }
}

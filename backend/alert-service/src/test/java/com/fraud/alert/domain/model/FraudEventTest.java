package com.fraud.alert.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FraudEventTest {

    @Test
    void shouldCreateFraudEventWithValidData() {
        // Given
        String userId = "user123";
        BigDecimal amount = new BigDecimal("1500.50");
        String riskLevel = "HIGH";
        List<String> reasons = List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION");

        // When
        FraudEvent event = new FraudEvent(userId, amount, riskLevel, reasons);

        // Then
        assertNotNull(event);
        assertEquals(userId, event.userId());
        assertEquals(amount, event.amount());
        assertEquals(riskLevel, event.riskLevel());
        assertEquals(reasons, event.reasons());
    }

    @Test
    void shouldAccessAllFieldsCorrectly() {
        // Given
        FraudEvent event = new FraudEvent(
                "user456",
                new BigDecimal("250.00"),
                "MEDIUM",
                List.of("UNUSUAL_LOCATION")
        );

        // When & Then
        assertEquals("user456", event.userId());
        assertEquals(new BigDecimal("250.00"), event.amount());
        assertEquals("MEDIUM", event.riskLevel());
        assertEquals(1, event.reasons().size());
        assertTrue(event.reasons().contains("UNUSUAL_LOCATION"));
    }

    @Test
    void shouldHandleBigDecimalAmountPrecision() {
        // Given
        BigDecimal preciseAmount = new BigDecimal("12345.6789");
        FraudEvent event = new FraudEvent(
                "user789",
                preciseAmount,
                "LOW",
                List.of()
        );

        // When & Then
        assertEquals(0, preciseAmount.compareTo(event.amount()));
        assertEquals(4, event.amount().scale());
    }

    @Test
    void shouldCreateEventWithEmptyReasons() {
        // Given
        FraudEvent event = new FraudEvent(
                "user000",
                new BigDecimal("100.00"),
                "LOW",
                List.of()
        );

        // When & Then
        assertNotNull(event.reasons());
        assertTrue(event.reasons().isEmpty());
    }
}

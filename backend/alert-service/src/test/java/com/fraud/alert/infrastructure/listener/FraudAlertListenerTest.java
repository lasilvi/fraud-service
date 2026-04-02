package com.fraud.alert.infrastructure.listener;

import com.fraud.alert.domain.model.FraudEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class FraudAlertListenerTest {

    @InjectMocks
    private FraudAlertListener listener;

    @Test
    void shouldProcessFraudEventWithoutErrors() {
        // Given
        FraudEvent event = new FraudEvent(
                "user123",
                new BigDecimal("1500.00"),
                "HIGH",
                List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION")
        );

        // When & Then
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldProcessEventWithLowRiskLevel() {
        // Given
        FraudEvent event = new FraudEvent(
                "user456",
                new BigDecimal("100.00"),
                "LOW",
                List.of()
        );

        // When & Then
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldProcessEventWithMediumRiskLevel() {
        // Given
        FraudEvent event = new FraudEvent(
                "user789",
                new BigDecimal("500.00"),
                "MEDIUM",
                List.of("UNUSUAL_LOCATION")
        );

        // When & Then
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldProcessEventWithHighRiskLevel() {
        // Given
        FraudEvent event = new FraudEvent(
                "user999",
                new BigDecimal("25000.00"),
                "HIGH",
                List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION")
        );

        // When & Then
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldProcessEventWithMultipleReasons() {
        // Given
        FraudEvent event = new FraudEvent(
                "user111",
                new BigDecimal("20000.00"),
                "HIGH",
                List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION", "FREQUENT_TRANSACTIONS")
        );

        // When & Then
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }
}

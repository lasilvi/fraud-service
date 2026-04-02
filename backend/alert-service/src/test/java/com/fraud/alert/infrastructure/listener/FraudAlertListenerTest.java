package com.fraud.alert.infrastructure.listener;

import com.fraud.alert.domain.model.FraudAlertEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FraudAlertListenerTest {

    @InjectMocks
    private FraudAlertListener listener;

    @Test
    void shouldHandleHighRiskAlert() {
        // Arrange
        FraudAlertEvent event = new FraudAlertEvent(
                "user123",
                new BigDecimal("25000.00"),
                "HIGH",
                List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION")
        );

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldHandleMediumRiskAlert() {
        // Arrange
        FraudAlertEvent event = new FraudAlertEvent(
                "user456",
                new BigDecimal("16000.00"),
                "MEDIUM",
                List.of("HIGH_AMOUNT")
        );

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldHandleLowRiskAlert() {
        // Arrange
        FraudAlertEvent event = new FraudAlertEvent(
                "user789",
                new BigDecimal("100.00"),
                "LOW",
                List.of()
        );

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
    }

    @Test
    void shouldProcessEventWithNullableFields() {
        // Arrange - testing record can handle different data
        FraudAlertEvent event = new FraudAlertEvent(
                "user000",
                BigDecimal.ZERO,
                "LOW",
                List.of()
        );

        // Act & Assert
        assertDoesNotThrow(() -> listener.handleFraudAlert(event));
        assertEquals("user000", event.userId());
        assertEquals(BigDecimal.ZERO, event.amount());
    }
}

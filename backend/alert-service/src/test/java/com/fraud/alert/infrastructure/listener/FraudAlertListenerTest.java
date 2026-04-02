package com.fraud.alert.infrastructure.listener;

import com.fraud.alert.application.port.out.SaveFraudAlertPort;
import com.fraud.alert.domain.model.FraudAlert;
import com.fraud.alert.domain.model.FraudAlertEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FraudAlertListenerTest {

    @Mock
    private SaveFraudAlertPort saveFraudAlertPort;

    @InjectMocks
    private FraudAlertListener listener;

    @Test
    void shouldSaveAlertWhenMessageReceived() {
        FraudAlertEvent event = new FraudAlertEvent(
                "user123", new BigDecimal("25000.00"), "HIGH",
                List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"));

        when(saveFraudAlertPort.save(any(FraudAlert.class))).thenReturn(
                new FraudAlert(1L, "user123", new BigDecimal("25000.00"), "HIGH",
                        List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"), null));

        listener.handleFraudAlert(event);

        ArgumentCaptor<FraudAlert> captor = ArgumentCaptor.forClass(FraudAlert.class);
        verify(saveFraudAlertPort).save(captor.capture());

        FraudAlert saved = captor.getValue();
        assertNull(saved.id());
        assertEquals("user123", saved.userId());
        assertEquals(new BigDecimal("25000.00"), saved.amount());
        assertEquals("HIGH", saved.riskLevel());
        assertEquals(List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"), saved.reasons());
    }

    @Test
    void shouldSaveMediumRiskAlert() {
        FraudAlertEvent event = new FraudAlertEvent(
                "user456", new BigDecimal("16000.00"), "MEDIUM",
                List.of("HIGH_AMOUNT"));

        when(saveFraudAlertPort.save(any(FraudAlert.class))).thenReturn(
                new FraudAlert(2L, "user456", new BigDecimal("16000.00"), "MEDIUM",
                        List.of("HIGH_AMOUNT"), null));

        listener.handleFraudAlert(event);

        verify(saveFraudAlertPort).save(any(FraudAlert.class));
    }
}

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

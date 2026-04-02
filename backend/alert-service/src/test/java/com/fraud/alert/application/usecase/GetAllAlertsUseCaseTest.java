package com.fraud.alert.application.usecase;

import com.fraud.alert.application.port.out.GetAllFraudAlertsPort;
import com.fraud.alert.domain.model.FraudAlert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllAlertsUseCaseTest {

    @Mock
    private GetAllFraudAlertsPort getAllPort;

    @InjectMocks
    private GetAllAlertsUseCase useCase;

    @Test
    void shouldReturnAllAlerts() {
        FraudAlert alert = new FraudAlert(1L, "user1", new BigDecimal("25000"),
                "HIGH", List.of("HIGH_AMOUNT"), LocalDateTime.now());
        when(getAllPort.getAll()).thenReturn(List.of(alert));

        List<FraudAlert> result = useCase.execute();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).userId());
        verify(getAllPort).getAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoAlerts() {
        when(getAllPort.getAll()).thenReturn(Collections.emptyList());

        List<FraudAlert> result = useCase.execute();

        assertTrue(result.isEmpty());
        verify(getAllPort).getAll();
    }
}

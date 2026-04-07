package com.fraud.alert.infrastructure.controller;

import com.fraud.alert.application.usecase.GetAllAlertsUseCase;
import com.fraud.alert.domain.model.FraudAlert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertControllerTest {

    @Mock
    private GetAllAlertsUseCase getAllAlertsUseCase;

    @InjectMocks
    private AlertController controller;

    @Test
    void shouldReturnAlertsWithOkStatus() {
        FraudAlert alert = new FraudAlert(1L, "user1", new BigDecimal("25000"),
                "HIGH", List.of("HIGH_AMOUNT"), LocalDateTime.now());
        when(getAllAlertsUseCase.execute()).thenReturn(List.of(alert));

        ResponseEntity<List<FraudAlert>> response = controller.getAlerts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("user1", response.getBody().get(0).userId());
        verify(getAllAlertsUseCase).execute();
    }

    @Test
    void shouldReturnEmptyListWithOkStatus() {
        when(getAllAlertsUseCase.execute()).thenReturn(Collections.emptyList());

        ResponseEntity<List<FraudAlert>> response = controller.getAlerts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}

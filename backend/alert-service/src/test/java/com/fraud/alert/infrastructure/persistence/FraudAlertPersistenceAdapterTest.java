package com.fraud.alert.infrastructure.persistence;

import com.fraud.alert.domain.model.FraudAlert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FraudAlertPersistenceAdapterTest {

    @Mock
    private FraudAlertJpaRepository repository;

    @InjectMocks
    private FraudAlertPersistenceAdapter adapter;

    @Test
    void shouldSaveAlertAndReturnMappedDomain() {
        LocalDateTime now = LocalDateTime.now();
        FraudAlert alert = new FraudAlert(null, "user1", new BigDecimal("25000"),
                "HIGH", List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"), now);

        FraudAlertEntity savedEntity = new FraudAlertEntity();
        savedEntity.setId(1L);
        savedEntity.setUserId("user1");
        savedEntity.setAmount(new BigDecimal("25000"));
        savedEntity.setRiskLevel("HIGH");
        savedEntity.setReasons("HIGH_AMOUNT,UNUSUAL_LOCATION");
        savedEntity.setTimestamp(now);

        when(repository.save(any(FraudAlertEntity.class))).thenReturn(savedEntity);

        FraudAlert result = adapter.save(alert);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("user1", result.userId());
        assertEquals("HIGH", result.riskLevel());
        assertEquals(List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"), result.reasons());

        ArgumentCaptor<FraudAlertEntity> captor = ArgumentCaptor.forClass(FraudAlertEntity.class);
        verify(repository).save(captor.capture());
        assertEquals("HIGH_AMOUNT,UNUSUAL_LOCATION", captor.getValue().getReasons());
    }

    @Test
    void shouldGetAllAlertsSortedByTimestampDesc() {
        FraudAlertEntity entity = new FraudAlertEntity();
        entity.setId(1L);
        entity.setUserId("user1");
        entity.setAmount(new BigDecimal("25000"));
        entity.setRiskLevel("HIGH");
        entity.setReasons("HIGH_AMOUNT");
        entity.setTimestamp(LocalDateTime.now());

        when(repository.findAllByOrderByTimestampDesc()).thenReturn(List.of(entity));

        List<FraudAlert> result = adapter.getAll();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).userId());
        assertEquals(List.of("HIGH_AMOUNT"), result.get(0).reasons());
        verify(repository).findAllByOrderByTimestampDesc();
    }

    @Test
    void shouldHandleEmptyReasons() {
        FraudAlertEntity entity = new FraudAlertEntity();
        entity.setId(1L);
        entity.setUserId("user1");
        entity.setAmount(new BigDecimal("100"));
        entity.setRiskLevel("LOW");
        entity.setReasons("");
        entity.setTimestamp(LocalDateTime.now());

        when(repository.findAllByOrderByTimestampDesc()).thenReturn(List.of(entity));

        List<FraudAlert> result = adapter.getAll();

        assertTrue(result.get(0).reasons().isEmpty());
    }
}

package com.fraud.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fraud.infrastructure.client.ConfigServiceClient;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FraudThresholdProviderAdapterTest {

    @Mock
    private ConfigServiceClient configServiceClient;

    @InjectMocks
    private FraudThresholdProviderAdapter adapter;

    @Test
    void getThreshold_shouldReturnValueFromConfigService() {
        when(configServiceClient.getThreshold()).thenReturn(new BigDecimal("20000"));

        BigDecimal result = adapter.getThreshold();

        assertEquals(new BigDecimal("20000"), result);
    }

    @Test
    void getThreshold_shouldDelegateToConfigServiceClient() {
        when(configServiceClient.getThreshold()).thenReturn(new BigDecimal("15000"));

        BigDecimal result = adapter.getThreshold();

        assertEquals(new BigDecimal("15000"), result);
    }
}

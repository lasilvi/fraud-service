package com.fraud.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.fraud.infrastructure.client.ConfigServiceClient;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserLocationProviderAdapterTest {

    @Mock
    private ConfigServiceClient configServiceClient;

    @InjectMocks
    private UserLocationProviderAdapter adapter;

    @Test
    void getUsualCountry_shouldDelegateToConfigServiceClient() {
        when(configServiceClient.getUserLocation("user123")).thenReturn(Optional.of("FR"));

        Optional<String> result = adapter.getUsualCountry("user123");

        assertTrue(result.isPresent());
        assertEquals("FR", result.get());
    }

    @Test
    void getUsualCountry_shouldReturnEmpty_whenClientReturnsEmpty() {
        when(configServiceClient.getUserLocation("unknown")).thenReturn(Optional.empty());

        Optional<String> result = adapter.getUsualCountry("unknown");

        assertTrue(result.isEmpty());
    }
}

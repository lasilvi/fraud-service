package com.fraud.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    void getUsualCountry_shouldReturnUserLocation_whenConfigured() {
        when(configServiceClient.getUserLocation("user123")).thenReturn(Optional.of("FR"));

        Optional<String> result = adapter.getUsualCountry("user123");

        assertTrue(result.isPresent());
        assertEquals("FR", result.get());
    }

    @Test
    void getUsualCountry_shouldNotFallbackToDefault_whenSpecificUserExists() {
        when(configServiceClient.getUserLocation("user456")).thenReturn(Optional.of("FR"));

        Optional<String> result = adapter.getUsualCountry("user456");

        assertTrue(result.isPresent());
        assertEquals("FR", result.get());
        verify(configServiceClient, never()).getUserLocation("default");
    }

    @Test
    void getUsualCountry_shouldFallbackToDefault_whenSpecificUserNotFound() {
        when(configServiceClient.getUserLocation("user123")).thenReturn(Optional.empty());
        when(configServiceClient.getUserLocation("default")).thenReturn(Optional.of("US"));

        Optional<String> result = adapter.getUsualCountry("user123");

        assertTrue(result.isPresent());
        assertEquals("US", result.get());
    }

    @Test
    void getUsualCountry_shouldReturnEmpty_whenBothUserAndDefaultNotFound() {
        when(configServiceClient.getUserLocation("unknown")).thenReturn(Optional.empty());
        when(configServiceClient.getUserLocation("default")).thenReturn(Optional.empty());

        Optional<String> result = adapter.getUsualCountry("unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void getUsualCountry_shouldNotDoubleQuery_whenUserIdIsDefault() {
        when(configServiceClient.getUserLocation("default")).thenReturn(Optional.of("CO"));

        Optional<String> result = adapter.getUsualCountry("default");

        assertTrue(result.isPresent());
        assertEquals("CO", result.get());
    }
}

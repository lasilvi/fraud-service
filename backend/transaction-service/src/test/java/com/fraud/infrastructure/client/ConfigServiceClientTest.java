package com.fraud.infrastructure.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ConfigServiceClientTest {

    private static final String BASE_URL = "http://localhost:8081";

    @Mock
    private RestTemplate restTemplate;

    private ConfigServiceClient client;

    @BeforeEach
    void setUp() {
        client = new ConfigServiceClient(restTemplate, BASE_URL);
    }

    @Test
    void getThreshold_shouldReturnValueFromConfigService() {
        Map<String, Object> responseBody = Map.of("threshold", 20000);
        ResponseEntity<Map> response = ResponseEntity.ok(responseBody);
        when(restTemplate.getForEntity(eq(BASE_URL + "/api/v1/config/threshold"), eq(Map.class)))
            .thenReturn(response);

        BigDecimal result = client.getThreshold();

        assertEquals(new BigDecimal("20000"), result);
    }

    @Test
    void getThreshold_shouldReturnDefault_whenServiceUnavailable() {
        when(restTemplate.getForEntity(eq(BASE_URL + "/api/v1/config/threshold"), eq(Map.class)))
            .thenThrow(new RestClientException("Connection refused"));

        BigDecimal result = client.getThreshold();

        assertEquals(new BigDecimal("15000"), result);
    }

    @Test
    void getThreshold_shouldReturnDefault_whenResponseBodyIsNull() {
        ResponseEntity<Map> response = ResponseEntity.ok(null);
        when(restTemplate.getForEntity(eq(BASE_URL + "/api/v1/config/threshold"), eq(Map.class)))
            .thenReturn(response);

        BigDecimal result = client.getThreshold();

        assertEquals(new BigDecimal("15000"), result);
    }

    @Test
    void getUserLocation_shouldReturnUsualCountry_whenUserExists() {
        Map<String, Object> responseBody = Map.of("userId", "user123", "usualCountry", "US");
        ResponseEntity<Map> response = ResponseEntity.ok(responseBody);
        when(restTemplate.getForEntity(eq(BASE_URL + "/api/v1/config/user-location/user123"), eq(Map.class)))
            .thenReturn(response);

        Optional<String> result = client.getUserLocation("user123");

        assertTrue(result.isPresent());
        assertEquals("US", result.get());
    }

    @Test
    void getUserLocation_shouldReturnEmpty_when404NotFound() {
        when(restTemplate.getForEntity(eq(BASE_URL + "/api/v1/config/user-location/unknown"), eq(Map.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Optional<String> result = client.getUserLocation("unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void getUserLocation_shouldReturnEmpty_whenServiceUnavailable() {
        when(restTemplate.getForEntity(eq(BASE_URL + "/api/v1/config/user-location/user123"), eq(Map.class)))
            .thenThrow(new RestClientException("Connection refused"));

        Optional<String> result = client.getUserLocation("user123");

        assertTrue(result.isEmpty());
    }
}

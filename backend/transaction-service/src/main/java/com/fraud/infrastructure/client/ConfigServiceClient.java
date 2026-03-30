package com.fraud.infrastructure.client;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ConfigServiceClient {

    private static final BigDecimal DEFAULT_THRESHOLD = new BigDecimal("15000");
    private static final String THRESHOLD_PATH = "/api/v1/config/threshold";
    private static final String USER_LOCATION_PATH = "/api/v1/config/user-location/";

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ConfigServiceClient(
        RestTemplate restTemplate,
        @Value("${config-service.url:http://localhost:8081}") String baseUrl
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @SuppressWarnings("unchecked")
    public BigDecimal getThreshold() {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                baseUrl + THRESHOLD_PATH, Map.class
            );
            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("threshold")) {
                return DEFAULT_THRESHOLD;
            }
            return new BigDecimal(body.get("threshold").toString());
        } catch (RestClientException e) {
            return DEFAULT_THRESHOLD;
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<String> getUserLocation(String userId) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                baseUrl + USER_LOCATION_PATH + userId, Map.class
            );
            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("usualCountry")) {
                return Optional.empty();
            }
            return Optional.of(body.get("usualCountry").toString());
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}

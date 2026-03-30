package com.fraud.application.port.out;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class UserLocationProviderTest {

    @Test
    void shouldDefineGetUsualCountryMethod() {
        UserLocationProvider provider = userId -> Optional.of("US");

        Optional<String> result = provider.getUsualCountry("user123");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        UserLocationProvider provider = userId -> Optional.empty();

        Optional<String> result = provider.getUsualCountry("unknown");

        assertTrue(result.isEmpty());
    }
}

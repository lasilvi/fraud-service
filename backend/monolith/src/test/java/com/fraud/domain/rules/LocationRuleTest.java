package com.fraud.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fraud.domain.model.FraudReason;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LocationRuleTest {

    private final LocationRule rule = new LocationRule();

    @Test
    void shouldReturnNoFraudWhenTransactionCountryMatchesUserCountry() {
        Optional<FraudReason> result = rule.evaluate("CO", "CO");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnUnusualLocationWhenTransactionCountryDiffersFromUserCountry() {
        Optional<FraudReason> result = rule.evaluate("CO", "US");

        assertEquals(Optional.of(FraudReason.UNUSUAL_LOCATION), result);
    }
}

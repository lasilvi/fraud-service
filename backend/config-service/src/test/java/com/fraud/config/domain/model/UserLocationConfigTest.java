package com.fraud.config.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserLocationConfigTest {

	@Test
	void shouldCreateValidUserLocationConfig() {
		UserLocationConfig config = new UserLocationConfig("user123", "CO");
		assertEquals("user123", config.userId());
		assertEquals("CO", config.usualCountry());
	}

	@Test
	void shouldThrowExceptionWhenUserIdIsNull() {
		assertThrows(IllegalArgumentException.class,
			() -> new UserLocationConfig(null, "CO"));
	}

	@Test
	void shouldThrowExceptionWhenUserIdIsBlank() {
		assertThrows(IllegalArgumentException.class,
			() -> new UserLocationConfig("  ", "CO"));
	}

	@Test
	void shouldThrowExceptionWhenUsualCountryIsNull() {
		assertThrows(IllegalArgumentException.class,
			() -> new UserLocationConfig("user123", null));
	}

	@Test
	void shouldThrowExceptionWhenUsualCountryIsBlank() {
		assertThrows(IllegalArgumentException.class,
			() -> new UserLocationConfig("user123", ""));
	}
}

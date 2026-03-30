package com.fraud.config.domain.model;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThresholdConfigTest {

	@Test
	void shouldCreateValidThresholdConfig() {
		ThresholdConfig config = new ThresholdConfig(new BigDecimal("20000"));
		assertEquals(new BigDecimal("20000"), config.threshold());
	}

	@Test
	void shouldCreateThresholdConfigWithZero() {
		ThresholdConfig config = new ThresholdConfig(BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, config.threshold());
	}

	@Test
	void shouldThrowExceptionWhenThresholdIsNegative() {
		assertThrows(IllegalArgumentException.class,
			() -> new ThresholdConfig(new BigDecimal("-1")));
	}

	@Test
	void shouldThrowExceptionWhenThresholdIsNull() {
		assertThrows(IllegalArgumentException.class,
			() -> new ThresholdConfig(null));
	}
}

package com.fraud.config.domain.model;

import java.math.BigDecimal;

public record ThresholdConfig(BigDecimal threshold) {

	public ThresholdConfig {
		if (threshold == null) {
			throw new IllegalArgumentException("Threshold cannot be null");
		}
		if (threshold.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Threshold cannot be negative");
		}
	}
}

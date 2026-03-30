package com.fraud.config.infrastructure.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ThresholdConfigDTO(
	@NotNull(message = "Threshold is required")
	@DecimalMin(value = "0", message = "Threshold must be zero or positive")
	BigDecimal threshold
) {
}

package com.fraud.config.infrastructure.controller.dto;

import com.fraud.config.domain.model.RiskLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RiskRuleConfigDTO(
	Long id,
	@NotBlank(message = "Condition is required")
	String condition,
	@NotNull(message = "Risk level is required")
	RiskLevel riskLevel
) {
}

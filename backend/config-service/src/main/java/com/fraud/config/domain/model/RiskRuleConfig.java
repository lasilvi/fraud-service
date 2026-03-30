package com.fraud.config.domain.model;

public record RiskRuleConfig(Long id, String condition, RiskLevel riskLevel) {

	public RiskRuleConfig {
		if (condition == null || condition.isBlank()) {
			throw new IllegalArgumentException("Condition cannot be null or blank");
		}
		if (riskLevel == null) {
			throw new IllegalArgumentException("Risk level cannot be null");
		}
	}
}

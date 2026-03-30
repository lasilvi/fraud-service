package com.fraud.config.domain.model;

public record UserLocationConfig(String userId, String usualCountry) {

	public UserLocationConfig {
		if (userId == null || userId.isBlank()) {
			throw new IllegalArgumentException("User ID cannot be null or blank");
		}
		if (usualCountry == null || usualCountry.isBlank()) {
			throw new IllegalArgumentException("Usual country cannot be null or blank");
		}
	}
}

package com.fraud.config.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLocationConfigDTO(
	@NotBlank(message = "User ID is required")
	String userId,
	@NotBlank(message = "Usual country is required")
	String usualCountry
) {
}

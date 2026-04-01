package com.fraud.infrastructure.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record FraudEvaluationRequest(
	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", inclusive = true, message = "amount must be greater than or equal to 0")
	BigDecimal amount,
	@NotBlank(message = "transactionCountry is required")
	@Size(min = 2, max = 3, message = "transactionCountry must have length between 2 and 3")
	String transactionCountry,
	String ip,
	@NotBlank(message = "userId is required")
	@Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "userId contains invalid characters")
	String userId
) {
}

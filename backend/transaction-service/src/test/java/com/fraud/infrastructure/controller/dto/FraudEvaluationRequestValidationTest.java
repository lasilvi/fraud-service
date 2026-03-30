package com.fraud.infrastructure.controller.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FraudEvaluationRequestValidationTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void shouldHaveNoViolationsForValidRequest() {
		FraudEvaluationRequest request = new FraudEvaluationRequest(BigDecimal.valueOf(1000), "US", "CO", "192.168.1.1", "user123");

		Set<ConstraintViolation<FraudEvaluationRequest>> violations = validator.validate(request);

		assertTrue(violations.isEmpty());
	}

	@Test
	void shouldReportViolationsForInvalidRequest() {
		FraudEvaluationRequest request = new FraudEvaluationRequest(BigDecimal.valueOf(-1), "", null, null, null);

		Set<ConstraintViolation<FraudEvaluationRequest>> violations = validator.validate(request);

		assertFalse(violations.isEmpty());
		assertEquals(4, violations.size());
	}

	@Test
	void shouldRejectBlankUserId() {
		FraudEvaluationRequest request = new FraudEvaluationRequest(BigDecimal.valueOf(1000), "US", "CO", "192.168.1.1", "");

		Set<ConstraintViolation<FraudEvaluationRequest>> violations = validator.validate(request);

		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("userId is required")));
	}
}

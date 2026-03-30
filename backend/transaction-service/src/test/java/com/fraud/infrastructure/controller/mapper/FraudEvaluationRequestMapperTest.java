package com.fraud.infrastructure.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fraud.domain.model.Transaction;
import com.fraud.infrastructure.controller.dto.FraudEvaluationRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FraudEvaluationRequestMapperTest {

	private final FraudEvaluationRequestMapper mapper = new FraudEvaluationRequestMapper();

	@Test
	void shouldMapRequestToDomainTransaction() {
		FraudEvaluationRequest request = new FraudEvaluationRequest(BigDecimal.valueOf(12000), "US", "CO", "192.168.1.1", "user789");

		Transaction result = mapper.toDomain(request);

		assertEquals(request.amount(), result.amount());
		assertEquals(request.transactionCountry(), result.transactionCountry());
		assertEquals(request.userCountry(), result.userCountry());
		assertEquals(request.ip(), result.ip());
		assertEquals(request.userId(), result.userId());
	}
}

package com.fraud.infrastructure.controller.mapper;

import com.fraud.domain.model.Transaction;
import com.fraud.infrastructure.controller.dto.FraudEvaluationRequest;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class FraudEvaluationRequestMapper {

	public Transaction toDomain(FraudEvaluationRequest request) {
		return new Transaction(
			UUID.randomUUID().toString(),
			request.amount(),
			request.transactionCountry(),
			request.ip(),
			Instant.now(),
			request.userId()
		);
	}
}

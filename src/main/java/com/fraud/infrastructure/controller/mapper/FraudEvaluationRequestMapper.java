package com.fraud.infrastructure.controller.mapper;

import com.fraud.domain.model.Transaction;
import com.fraud.infrastructure.controller.dto.FraudEvaluationRequest;
import org.springframework.stereotype.Component;

@Component
public class FraudEvaluationRequestMapper {

	public Transaction toDomain(FraudEvaluationRequest request) {
		return new Transaction(request.amount(), request.transactionCountry(), request.userCountry());
	}
}

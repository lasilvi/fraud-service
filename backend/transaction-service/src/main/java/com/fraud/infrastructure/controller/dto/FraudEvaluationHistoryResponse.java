package com.fraud.infrastructure.controller.dto;

import com.fraud.application.model.FraudEvaluationHistoryItem;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record FraudEvaluationHistoryResponse(
	Long id,
	BigDecimal amount,
	String transactionCountry,
	String userCountry,
	boolean suspicious,
	RiskLevel riskLevel,
	Set<FraudReason> reasons,
	Instant evaluatedAt
) {

	public static FraudEvaluationHistoryResponse fromApplication(FraudEvaluationHistoryItem item) {
		return new FraudEvaluationHistoryResponse(
			item.id(),
			item.amount(),
			item.transactionCountry(),
			item.userCountry(),
			item.suspicious(),
			item.riskLevel(),
			item.reasons(),
			item.evaluatedAt()
		);
	}
}

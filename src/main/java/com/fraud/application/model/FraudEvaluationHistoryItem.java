package com.fraud.application.model;

import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record FraudEvaluationHistoryItem(
	Long id,
	BigDecimal amount,
	String transactionCountry,
	String userCountry,
	boolean suspicious,
	RiskLevel riskLevel,
	Set<FraudReason> reasons,
	Instant evaluatedAt
) {
}

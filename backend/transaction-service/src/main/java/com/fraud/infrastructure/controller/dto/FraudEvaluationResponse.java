package com.fraud.infrastructure.controller.dto;

import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import java.util.Set;

public record FraudEvaluationResponse(boolean suspicious, RiskLevel riskLevel, Set<FraudReason> reasons) {

	public static FraudEvaluationResponse fromDomain(FraudEvaluationResult result) {
		return new FraudEvaluationResponse(result.suspicious(), result.riskLevel(), result.reasons());
	}
}

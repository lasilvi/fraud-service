package com.fraud.domain.service;

import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import java.util.Set;

public class FraudDomainService {

	public FraudEvaluationResult evaluate(Set<FraudReason> reasons) {
		int triggeredRules = reasons.size();
		RiskLevel riskLevel = mapRiskLevel(triggeredRules);
		boolean suspicious = triggeredRules > 0;

		return new FraudEvaluationResult(suspicious, riskLevel, reasons);
	}

	private RiskLevel mapRiskLevel(int triggeredRules) {
		if (triggeredRules == 0) {
			return RiskLevel.LOW;
		}
		if (triggeredRules == 1) {
			return RiskLevel.MEDIUM;
		}
		return RiskLevel.HIGH;
	}
}

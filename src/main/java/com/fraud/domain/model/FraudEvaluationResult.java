package com.fraud.domain.model;

import java.util.Set;

public record FraudEvaluationResult(boolean suspicious, RiskLevel riskLevel, Set<FraudReason> reasons) {
}

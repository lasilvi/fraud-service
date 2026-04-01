package com.fraud.application.port.out;

import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.Transaction;

public interface FraudEvaluationAuditPort {

	void save(Transaction transaction, FraudEvaluationResult result, String resolvedUserCountry);
}

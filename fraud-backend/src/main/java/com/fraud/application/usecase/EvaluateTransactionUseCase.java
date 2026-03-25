package com.fraud.application.usecase;

import com.fraud.application.port.out.FraudEvaluationAuditPort;
import com.fraud.application.port.out.FraudThresholdProvider;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.Transaction;
import com.fraud.domain.rules.AmountRule;
import com.fraud.domain.rules.LocationRule;
import com.fraud.domain.service.FraudDomainService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EvaluateTransactionUseCase {

	private final FraudThresholdProvider fraudThresholdProvider;
	private final FraudEvaluationAuditPort fraudEvaluationAuditPort;
	private final FraudDomainService fraudDomainService;

	public EvaluateTransactionUseCase(
		FraudThresholdProvider fraudThresholdProvider,
		FraudEvaluationAuditPort fraudEvaluationAuditPort
	) {
		this.fraudThresholdProvider = fraudThresholdProvider;
		this.fraudEvaluationAuditPort = fraudEvaluationAuditPort;
		this.fraudDomainService = new FraudDomainService();
	}

	public FraudEvaluationResult execute(Transaction transaction) {
		AmountRule amountRule = new AmountRule(fraudThresholdProvider.getThreshold());
		LocationRule locationRule = new LocationRule();

		Set<FraudReason> reasons = new HashSet<>();
		amountRule.evaluate(transaction.amount()).ifPresent(reasons::add);
		locationRule.evaluate(transaction.userCountry(), transaction.transactionCountry()).ifPresent(reasons::add);

		FraudEvaluationResult result = fraudDomainService.evaluate(reasons);
		fraudEvaluationAuditPort.save(transaction, result);
		return result;
	}
}

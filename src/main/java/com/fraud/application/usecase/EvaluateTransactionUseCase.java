package com.fraud.application.usecase;

import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.Transaction;
import com.fraud.domain.rules.AmountRule;
import com.fraud.domain.rules.LocationRule;
import com.fraud.domain.service.FraudDomainService;
import com.fraud.infrastructure.config.FraudProperties;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EvaluateTransactionUseCase {

	private final FraudProperties fraudProperties;
	private final FraudDomainService fraudDomainService;

	public EvaluateTransactionUseCase(FraudProperties fraudProperties) {
		this.fraudProperties = fraudProperties;
		this.fraudDomainService = new FraudDomainService();
	}

	public FraudEvaluationResult execute(Transaction transaction) {
		AmountRule amountRule = new AmountRule(fraudProperties.getThreshold());
		LocationRule locationRule = new LocationRule();

		Set<FraudReason> reasons = new HashSet<>();
		amountRule.evaluate(transaction.amount()).ifPresent(reasons::add);
		locationRule.evaluate(transaction.userCountry(), transaction.transactionCountry()).ifPresent(reasons::add);

		return fraudDomainService.evaluate(reasons);
	}
}

package com.fraud.application.usecase;

import com.fraud.application.port.out.FraudAlertPublisherPort;
import com.fraud.application.port.out.FraudEvaluationAuditPort;
import com.fraud.application.port.out.FraudThresholdProvider;
import com.fraud.application.port.out.SaveTransactionPort;
import com.fraud.application.port.out.UserLocationProvider;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.Transaction;
import com.fraud.domain.rules.AmountRule;
import com.fraud.domain.rules.LocationRule;
import com.fraud.domain.service.FraudDomainService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EvaluateTransactionUseCase {

	private final FraudThresholdProvider fraudThresholdProvider;
	private final FraudEvaluationAuditPort fraudEvaluationAuditPort;
	private final SaveTransactionPort saveTransactionPort;
	private final UserLocationProvider userLocationProvider;
	private final FraudAlertPublisherPort fraudAlertPublisherPort;
	private final FraudDomainService fraudDomainService;

	public EvaluateTransactionUseCase(
		FraudThresholdProvider fraudThresholdProvider,
		FraudEvaluationAuditPort fraudEvaluationAuditPort,
		SaveTransactionPort saveTransactionPort,
		UserLocationProvider userLocationProvider,
		FraudAlertPublisherPort fraudAlertPublisherPort
	) {
		this.fraudThresholdProvider = fraudThresholdProvider;
		this.fraudEvaluationAuditPort = fraudEvaluationAuditPort;
		this.saveTransactionPort = saveTransactionPort;
		this.userLocationProvider = userLocationProvider;
		this.fraudAlertPublisherPort = fraudAlertPublisherPort;
		this.fraudDomainService = new FraudDomainService();
	}

	public FraudEvaluationResult execute(Transaction transaction) {
		saveTransactionPort.save(transaction);

		AmountRule amountRule = new AmountRule(fraudThresholdProvider.getThreshold());

		Optional<String> usualCountry = userLocationProvider.getUsualCountry(transaction.userId());

		Set<FraudReason> reasons = new HashSet<>();
		amountRule.evaluate(transaction.amount()).ifPresent(reasons::add);

		if (usualCountry.isPresent()) {
			LocationRule locationRule = new LocationRule();
			locationRule.evaluate(usualCountry.get(), transaction.transactionCountry()).ifPresent(reasons::add);
		}

		FraudEvaluationResult result = fraudDomainService.evaluate(reasons);
		fraudEvaluationAuditPort.save(transaction, result, usualCountry.orElse(null));
		if (result.suspicious()) {
			List<String> reasonStrings = result.reasons().stream()
				.map(Enum::name)
				.toList();
			fraudAlertPublisherPort.publish(
				transaction.userId(),
				transaction.amount(),
				result.riskLevel().name(),
				reasonStrings
			);
		}
		return result;
	}
}

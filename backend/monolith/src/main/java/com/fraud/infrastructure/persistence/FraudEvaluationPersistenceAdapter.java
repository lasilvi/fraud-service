package com.fraud.infrastructure.persistence;

import com.fraud.application.port.out.FraudEvaluationAuditPort;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.Transaction;
import java.time.Instant;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FraudEvaluationPersistenceAdapter implements FraudEvaluationAuditPort {

	private final FraudEvaluationJpaRepository repository;

	public FraudEvaluationPersistenceAdapter(FraudEvaluationJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(Transaction transaction, FraudEvaluationResult result) {
		FraudEvaluationJpaEntity entity = new FraudEvaluationJpaEntity();
		entity.setTransactionId(transaction.id());
		entity.setAmount(transaction.amount());
		entity.setTransactionCountry(transaction.transactionCountry());
		entity.setUserCountry(transaction.userCountry());
		entity.setIp(transaction.ip());
		entity.setTransactionTimestamp(transaction.timestamp());
		entity.setSuspicious(result.suspicious());
		entity.setRiskLevel(result.riskLevel().name());
		entity.setReasons(joinReasons(result.reasons()));
		entity.setEvaluatedAt(Instant.now());

		repository.save(entity);
	}

	private String joinReasons(Set<FraudReason> reasons) {
		return reasons.stream()
			.map(Enum::name)
			.sorted(Comparator.naturalOrder())
			.collect(Collectors.joining(","));
	}
}

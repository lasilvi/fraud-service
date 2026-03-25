package com.fraud.infrastructure.persistence;

import com.fraud.application.model.FraudEvaluationHistoryItem;
import com.fraud.application.port.out.FraudEvaluationHistoryPort;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class FraudEvaluationHistoryPersistenceAdapter implements FraudEvaluationHistoryPort {

	private final FraudEvaluationJpaRepository repository;

	public FraudEvaluationHistoryPersistenceAdapter(FraudEvaluationJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<FraudEvaluationHistoryItem> findRecent(int limit) {
		return repository.findAllByOrderByEvaluatedAtDesc(PageRequest.of(0, limit))
			.stream()
			.map(this::toApplicationModel)
			.toList();
	}

	private FraudEvaluationHistoryItem toApplicationModel(FraudEvaluationJpaEntity entity) {
		return new FraudEvaluationHistoryItem(
			entity.getId(),
			entity.getAmount(),
			entity.getTransactionCountry(),
			entity.getUserCountry(),
			entity.isSuspicious(),
			RiskLevel.valueOf(entity.getRiskLevel()),
			parseReasons(entity.getReasons()),
			entity.getEvaluatedAt()
		);
	}

	private Set<FraudReason> parseReasons(String reasons) {
		if (reasons == null || reasons.isBlank()) {
			return Collections.emptySet();
		}
		return Arrays.stream(reasons.split(","))
			.map(String::trim)
			.filter(value -> !value.isEmpty())
			.map(FraudReason::valueOf)
			.collect(Collectors.toSet());
	}
}

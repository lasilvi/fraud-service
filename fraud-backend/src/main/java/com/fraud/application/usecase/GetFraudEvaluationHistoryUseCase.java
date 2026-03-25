package com.fraud.application.usecase;

import com.fraud.application.model.FraudEvaluationHistoryItem;
import com.fraud.application.port.out.FraudEvaluationHistoryPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetFraudEvaluationHistoryUseCase {

	private static final int DEFAULT_LIMIT = 20;
	private static final int MAX_LIMIT = 100;

	private final FraudEvaluationHistoryPort fraudEvaluationHistoryPort;

	public GetFraudEvaluationHistoryUseCase(FraudEvaluationHistoryPort fraudEvaluationHistoryPort) {
		this.fraudEvaluationHistoryPort = fraudEvaluationHistoryPort;
	}

	public List<FraudEvaluationHistoryItem> execute(Integer limit) {
		return fraudEvaluationHistoryPort.findRecent(sanitizeLimit(limit));
	}

	private int sanitizeLimit(Integer limit) {
		if (limit == null) {
			return DEFAULT_LIMIT;
		}
		if (limit < 1) {
			return 1;
		}
		if (limit > MAX_LIMIT) {
			return MAX_LIMIT;
		}
		return limit;
	}
}

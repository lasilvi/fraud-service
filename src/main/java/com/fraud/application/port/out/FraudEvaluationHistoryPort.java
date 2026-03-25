package com.fraud.application.port.out;

import com.fraud.application.model.FraudEvaluationHistoryItem;
import java.util.List;

public interface FraudEvaluationHistoryPort {

	List<FraudEvaluationHistoryItem> findRecent(int limit);
}

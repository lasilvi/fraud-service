package com.fraud.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fraud.application.model.FraudEvaluationHistoryItem;
import com.fraud.application.port.out.FraudEvaluationHistoryPort;
import com.fraud.domain.model.RiskLevel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetFraudEvaluationHistoryUseCaseTest {

	@Test
	void shouldUseDefaultLimitWhenNull() {
		CapturingHistoryPort historyPort = new CapturingHistoryPort();
		GetFraudEvaluationHistoryUseCase useCase = new GetFraudEvaluationHistoryUseCase(historyPort);

		useCase.execute(null);

		assertEquals(20, historyPort.lastLimit);
	}

	@Test
	void shouldClampLimitToMax() {
		CapturingHistoryPort historyPort = new CapturingHistoryPort();
		GetFraudEvaluationHistoryUseCase useCase = new GetFraudEvaluationHistoryUseCase(historyPort);

		useCase.execute(999);

		assertEquals(100, historyPort.lastLimit);
	}

	@Test
	void shouldClampLimitToMin() {
		CapturingHistoryPort historyPort = new CapturingHistoryPort();
		GetFraudEvaluationHistoryUseCase useCase = new GetFraudEvaluationHistoryUseCase(historyPort);

		useCase.execute(0);

		assertEquals(1, historyPort.lastLimit);
	}

	private static final class CapturingHistoryPort implements FraudEvaluationHistoryPort {

		private int lastLimit;

		@Override
		public List<FraudEvaluationHistoryItem> findRecent(int limit) {
			this.lastLimit = limit;
			return Collections.singletonList(
				new FraudEvaluationHistoryItem(
					1L,
					BigDecimal.valueOf(100),
					"US",
					"US",
					false,
					RiskLevel.LOW,
					Collections.emptySet(),
					Instant.now()
				)
			);
		}
	}
}

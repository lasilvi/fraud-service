package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.GetAllRiskRulesConfigPort;
import com.fraud.config.domain.model.RiskLevel;
import com.fraud.config.domain.model.RiskRuleConfig;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllRiskRulesUseCaseTest {

	private GetAllRiskRulesConfigPort getPort;
	private GetAllRiskRulesUseCase useCase;

	@BeforeEach
	void setUp() {
		getPort = mock(GetAllRiskRulesConfigPort.class);
		useCase = new GetAllRiskRulesUseCase(getPort);
	}

	@Test
	void shouldReturnAllRiskRules() {
		List<RiskRuleConfig> expected = List.of(
			new RiskRuleConfig(1L, "amount > 10000", RiskLevel.HIGH),
			new RiskRuleConfig(2L, "amount > 5000", RiskLevel.MEDIUM)
		);
		when(getPort.getAll()).thenReturn(expected);

		List<RiskRuleConfig> result = useCase.execute();

		assertEquals(2, result.size());
		assertEquals(expected, result);
	}

	@Test
	void shouldReturnEmptyListWhenNoRulesExist() {
		when(getPort.getAll()).thenReturn(List.of());

		List<RiskRuleConfig> result = useCase.execute();

		assertTrue(result.isEmpty());
	}
}

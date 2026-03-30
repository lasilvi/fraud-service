package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.SaveRiskRuleConfigPort;
import com.fraud.config.domain.model.RiskLevel;
import com.fraud.config.domain.model.RiskRuleConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateRiskRuleUseCaseTest {

	private SaveRiskRuleConfigPort savePort;
	private CreateRiskRuleUseCase useCase;

	@BeforeEach
	void setUp() {
		savePort = mock(SaveRiskRuleConfigPort.class);
		useCase = new CreateRiskRuleUseCase(savePort);
	}

	@Test
	void shouldCreateRiskRule() {
		RiskRuleConfig input = new RiskRuleConfig(null, "amount > 10000", RiskLevel.HIGH);
		RiskRuleConfig saved = new RiskRuleConfig(1L, "amount > 10000", RiskLevel.HIGH);
		when(savePort.save(input)).thenReturn(saved);

		RiskRuleConfig result = useCase.execute(input);

		assertEquals(saved, result);
		verify(savePort, times(1)).save(input);
	}
}

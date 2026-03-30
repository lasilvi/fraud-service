package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.DeleteRiskRuleConfigPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class DeleteRiskRuleUseCaseTest {

	private DeleteRiskRuleConfigPort deletePort;
	private DeleteRiskRuleUseCase useCase;

	@BeforeEach
	void setUp() {
		deletePort = mock(DeleteRiskRuleConfigPort.class);
		useCase = new DeleteRiskRuleUseCase(deletePort);
	}

	@Test
	void shouldDeleteRiskRule() {
		useCase.execute(1L);

		verify(deletePort, times(1)).delete(1L);
	}
}

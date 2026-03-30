package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.SaveThresholdConfigPort;
import com.fraud.config.domain.model.ThresholdConfig;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class UpdateThresholdUseCaseTest {

	private SaveThresholdConfigPort savePort;
	private UpdateThresholdUseCase useCase;

	@BeforeEach
	void setUp() {
		savePort = mock(SaveThresholdConfigPort.class);
		useCase = new UpdateThresholdUseCase(savePort);
	}

	@Test
	void shouldUpdateThreshold() {
		ThresholdConfig config = new ThresholdConfig(new BigDecimal("18000"));

		useCase.execute(config);

		verify(savePort, times(1)).save(config);
	}
}

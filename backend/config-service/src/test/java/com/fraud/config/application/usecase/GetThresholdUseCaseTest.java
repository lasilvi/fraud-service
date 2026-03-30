package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.GetThresholdConfigPort;
import com.fraud.config.domain.model.ThresholdConfig;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetThresholdUseCaseTest {

	private GetThresholdConfigPort getPort;
	private GetThresholdUseCase useCase;

	@BeforeEach
	void setUp() {
		getPort = mock(GetThresholdConfigPort.class);
		useCase = new GetThresholdUseCase(getPort);
	}

	@Test
	void shouldReturnCurrentThreshold() {
		ThresholdConfig expected = new ThresholdConfig(new BigDecimal("15000"));
		when(getPort.get()).thenReturn(Optional.of(expected));

		ThresholdConfig result = useCase.execute();

		assertEquals(expected, result);
	}

	@Test
	void shouldReturnDefaultWhenNoConfigExists() {
		when(getPort.get()).thenReturn(Optional.empty());

		ThresholdConfig result = useCase.execute();

		assertEquals(new BigDecimal("15000"), result.threshold());
	}
}

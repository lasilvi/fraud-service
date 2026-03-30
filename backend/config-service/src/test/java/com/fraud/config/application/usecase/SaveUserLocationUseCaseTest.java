package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.SaveUserLocationConfigPort;
import com.fraud.config.domain.model.UserLocationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class SaveUserLocationUseCaseTest {

	private SaveUserLocationConfigPort savePort;
	private SaveUserLocationUseCase useCase;

	@BeforeEach
	void setUp() {
		savePort = mock(SaveUserLocationConfigPort.class);
		useCase = new SaveUserLocationUseCase(savePort);
	}

	@Test
	void shouldSaveUserLocation() {
		UserLocationConfig config = new UserLocationConfig("user123", "CO");

		useCase.execute(config);

		verify(savePort, times(1)).save(config);
	}
}

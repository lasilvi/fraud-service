package com.fraud.config.application.usecase;

import com.fraud.config.application.port.out.GetUserLocationConfigPort;
import com.fraud.config.domain.model.UserLocationConfig;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserLocationUseCaseTest {

	private GetUserLocationConfigPort getPort;
	private GetUserLocationUseCase useCase;

	@BeforeEach
	void setUp() {
		getPort = mock(GetUserLocationConfigPort.class);
		useCase = new GetUserLocationUseCase(getPort);
	}

	@Test
	void shouldReturnUserLocationWhenExists() {
		UserLocationConfig expected = new UserLocationConfig("user123", "CO");
		when(getPort.getByUserId("user123")).thenReturn(Optional.of(expected));

		Optional<UserLocationConfig> result = useCase.execute("user123");

		assertTrue(result.isPresent());
		assertEquals(expected, result.get());
	}

	@Test
	void shouldReturnEmptyWhenUserNotFound() {
		when(getPort.getByUserId("unknown")).thenReturn(Optional.empty());

		Optional<UserLocationConfig> result = useCase.execute("unknown");

		assertTrue(result.isEmpty());
	}
}

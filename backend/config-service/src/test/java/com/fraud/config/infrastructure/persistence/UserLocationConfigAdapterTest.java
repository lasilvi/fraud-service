package com.fraud.config.infrastructure.persistence;

import com.fraud.config.domain.model.UserLocationConfig;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserLocationConfigAdapterTest {

	private UserLocationConfigRepository repository;
	private UserLocationConfigAdapter adapter;

	@BeforeEach
	void setUp() {
		repository = mock(UserLocationConfigRepository.class);
		adapter = new UserLocationConfigAdapter(repository);
	}

	@Test
	void shouldSaveUserLocationConfig() {
		UserLocationConfig config = new UserLocationConfig("user123", "CO");

		adapter.save(config);

		verify(repository, times(1)).save(any(UserLocationConfigEntity.class));
	}

	@Test
	void shouldUpdateExistingUserLocation() {
		UserLocationConfigEntity existing = new UserLocationConfigEntity();
		existing.setId(1L);
		existing.setUserId("user123");
		existing.setUsualCountry("CO");
		when(repository.findByUserId("user123")).thenReturn(Optional.of(existing));

		adapter.save(new UserLocationConfig("user123", "US"));

		verify(repository).save(argThat(entity ->
			entity.getUsualCountry().equals("US") && entity.getId() != null
		));
	}

	@Test
	void shouldGetUserLocationByUserId() {
		UserLocationConfigEntity entity = new UserLocationConfigEntity();
		entity.setUserId("user123");
		entity.setUsualCountry("CO");
		when(repository.findByUserId("user123")).thenReturn(Optional.of(entity));

		Optional<UserLocationConfig> result = adapter.getByUserId("user123");

		assertTrue(result.isPresent());
		assertEquals("CO", result.get().usualCountry());
	}

	@Test
	void shouldReturnEmptyWhenUserNotFound() {
		when(repository.findByUserId("unknown")).thenReturn(Optional.empty());

		Optional<UserLocationConfig> result = adapter.getByUserId("unknown");

		assertTrue(result.isEmpty());
	}
}

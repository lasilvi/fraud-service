package com.fraud.config.infrastructure.persistence;

import com.fraud.config.domain.model.ThresholdConfig;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ThresholdConfigAdapterTest {

	private ThresholdConfigRepository repository;
	private ThresholdConfigAdapter adapter;

	@BeforeEach
	void setUp() {
		repository = mock(ThresholdConfigRepository.class);
		adapter = new ThresholdConfigAdapter(repository);
	}

	@Test
	void shouldSaveThresholdConfig() {
		ThresholdConfig config = new ThresholdConfig(new BigDecimal("18000"));

		adapter.save(config);

		verify(repository, times(1)).save(any(ThresholdConfigEntity.class));
	}

	@Test
	void shouldUpdateExistingThresholdConfig() {
		ThresholdConfigEntity existing = new ThresholdConfigEntity();
		existing.setId(1L);
		existing.setThreshold(new BigDecimal("15000"));
		when(repository.findAll()).thenReturn(List.of(existing));

		adapter.save(new ThresholdConfig(new BigDecimal("20000")));

		verify(repository).save(argThat(entity ->
			entity.getThreshold().compareTo(new BigDecimal("20000")) == 0
			&& entity.getId() != null
		));
	}

	@Test
	void shouldGetCurrentThreshold() {
		ThresholdConfigEntity entity = new ThresholdConfigEntity();
		entity.setId(1L);
		entity.setThreshold(new BigDecimal("15000"));
		when(repository.findAll()).thenReturn(List.of(entity));

		Optional<ThresholdConfig> result = adapter.get();

		assertTrue(result.isPresent());
		assertEquals(new BigDecimal("15000"), result.get().threshold());
	}

	@Test
	void shouldReturnEmptyWhenNoThresholdConfigExists() {
		when(repository.findAll()).thenReturn(List.of());

		Optional<ThresholdConfig> result = adapter.get();

		assertTrue(result.isEmpty());
	}
}

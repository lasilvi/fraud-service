package com.fraud.config.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fraud.config.application.port.out.GetThresholdConfigPort;
import com.fraud.config.application.port.out.SaveThresholdConfigPort;
import com.fraud.config.domain.model.ThresholdConfig;

@Component
public class ThresholdConfigAdapter implements SaveThresholdConfigPort, GetThresholdConfigPort {

	private final ThresholdConfigRepository repository;

	public ThresholdConfigAdapter(ThresholdConfigRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(ThresholdConfig config) {
		ThresholdConfigEntity entity = repository.findAll().stream().findFirst()
			.orElse(new ThresholdConfigEntity());
		entity.setThreshold(config.threshold());
		repository.save(entity);
	}

	@Override
	public Optional<ThresholdConfig> get() {
		return repository.findAll().stream().findFirst()
			.map(entity -> new ThresholdConfig(entity.getThreshold()));
	}
}

package com.fraud.config.application.port.out;

import java.util.Optional;

import com.fraud.config.domain.model.ThresholdConfig;

public interface GetThresholdConfigPort {
	Optional<ThresholdConfig> get();
}

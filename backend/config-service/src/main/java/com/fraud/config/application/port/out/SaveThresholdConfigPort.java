package com.fraud.config.application.port.out;

import com.fraud.config.domain.model.ThresholdConfig;

public interface SaveThresholdConfigPort {
	void save(ThresholdConfig config);
}

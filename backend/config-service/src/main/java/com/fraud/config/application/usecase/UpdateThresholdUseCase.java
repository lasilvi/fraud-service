package com.fraud.config.application.usecase;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.SaveThresholdConfigPort;
import com.fraud.config.domain.model.ThresholdConfig;

@Service
public class UpdateThresholdUseCase {

	private final SaveThresholdConfigPort savePort;

	public UpdateThresholdUseCase(SaveThresholdConfigPort savePort) {
		this.savePort = savePort;
	}

	public void execute(ThresholdConfig config) {
		savePort.save(config);
	}
}

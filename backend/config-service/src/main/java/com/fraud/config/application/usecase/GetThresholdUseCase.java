package com.fraud.config.application.usecase;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.GetThresholdConfigPort;
import com.fraud.config.domain.model.ThresholdConfig;

@Service
public class GetThresholdUseCase {

	private static final BigDecimal DEFAULT_THRESHOLD = new BigDecimal("15000");

	private final GetThresholdConfigPort getPort;

	public GetThresholdUseCase(GetThresholdConfigPort getPort) {
		this.getPort = getPort;
	}

	public ThresholdConfig execute() {
		return getPort.get().orElse(new ThresholdConfig(DEFAULT_THRESHOLD));
	}
}

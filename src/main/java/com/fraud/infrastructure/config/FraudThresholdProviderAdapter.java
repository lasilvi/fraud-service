package com.fraud.infrastructure.config;

import com.fraud.application.port.out.FraudThresholdProvider;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FraudThresholdProviderAdapter implements FraudThresholdProvider {

	private final FraudProperties fraudProperties;

	public FraudThresholdProviderAdapter(FraudProperties fraudProperties) {
		this.fraudProperties = fraudProperties;
	}

	@Override
	public BigDecimal getThreshold() {
		return fraudProperties.getThreshold();
	}
}

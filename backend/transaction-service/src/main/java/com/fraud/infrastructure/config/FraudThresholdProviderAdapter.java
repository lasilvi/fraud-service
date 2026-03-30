package com.fraud.infrastructure.config;

import com.fraud.application.port.out.FraudThresholdProvider;
import com.fraud.infrastructure.client.ConfigServiceClient;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FraudThresholdProviderAdapter implements FraudThresholdProvider {

	private final ConfigServiceClient configServiceClient;

	public FraudThresholdProviderAdapter(ConfigServiceClient configServiceClient) {
		this.configServiceClient = configServiceClient;
	}

	@Override
	public BigDecimal getThreshold() {
		return configServiceClient.getThreshold();
	}
}

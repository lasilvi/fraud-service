package com.fraud.application.port.out;

import java.math.BigDecimal;
import java.util.List;

public interface FraudAlertPublisherPort {
	void publish(String userId, BigDecimal amount, String riskLevel, List<String> reasons);
}

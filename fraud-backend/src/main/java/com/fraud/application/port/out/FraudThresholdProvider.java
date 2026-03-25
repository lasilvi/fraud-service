package com.fraud.application.port.out;

import java.math.BigDecimal;

public interface FraudThresholdProvider {

	BigDecimal getThreshold();
}

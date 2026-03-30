package com.fraud.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction(
	String id,
	BigDecimal amount,
	String transactionCountry,
	String userCountry,
	String ip,
	Instant timestamp,
	String userId
) {
}

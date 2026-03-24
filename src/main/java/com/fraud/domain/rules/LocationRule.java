package com.fraud.domain.rules;

import com.fraud.domain.model.FraudReason;
import java.util.Objects;
import java.util.Optional;

public class LocationRule {

	public Optional<FraudReason> evaluate(String userCountry, String transactionCountry) {
		Objects.requireNonNull(userCountry, "User country cannot be null");
		Objects.requireNonNull(transactionCountry, "Transaction country cannot be null");

		if (!userCountry.equals(transactionCountry)) {
			return Optional.of(FraudReason.UNUSUAL_LOCATION);
		}

		return Optional.empty();
	}
}

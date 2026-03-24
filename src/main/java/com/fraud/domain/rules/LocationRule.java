package com.fraud.domain.rules;

import com.fraud.domain.model.FraudReason;
import java.util.Objects;
import java.util.Optional;

public class LocationRule {

	private static final String USER_COUNTRY_NULL_MESSAGE = "User country cannot be null";
	private static final String TRANSACTION_COUNTRY_NULL_MESSAGE = "Transaction country cannot be null";

	public Optional<FraudReason> evaluate(String userCountry, String transactionCountry) {
		Objects.requireNonNull(userCountry, USER_COUNTRY_NULL_MESSAGE);
		Objects.requireNonNull(transactionCountry, TRANSACTION_COUNTRY_NULL_MESSAGE);

		if (isDifferentCountry(userCountry, transactionCountry)) {
			return Optional.of(FraudReason.UNUSUAL_LOCATION);
		}

		return Optional.empty();
	}

	private boolean isDifferentCountry(String userCountry, String transactionCountry) {
		return !userCountry.equals(transactionCountry);
	}
}

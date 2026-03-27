package com.fraud.application.port.out;

import com.fraud.domain.model.Transaction;

public interface SaveTransactionPort {
	void save(Transaction transaction);
}

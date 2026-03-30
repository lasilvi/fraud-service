package com.fraud.infrastructure.persistence;

import com.fraud.application.port.out.SaveTransactionPort;
import com.fraud.domain.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionPersistenceAdapter implements SaveTransactionPort {

	private final TransactionJpaRepository repository;

	public TransactionPersistenceAdapter(TransactionJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(Transaction transaction) {
		TransactionJpaEntity entity = new TransactionJpaEntity();
		entity.setId(transaction.id());
		entity.setAmount(transaction.amount());
		entity.setTransactionCountry(transaction.transactionCountry());
		entity.setUserCountry(transaction.userCountry());
		entity.setIp(transaction.ip());
		entity.setTimestamp(transaction.timestamp());
		entity.setUserId(transaction.userId());

		repository.save(entity);
	}
}

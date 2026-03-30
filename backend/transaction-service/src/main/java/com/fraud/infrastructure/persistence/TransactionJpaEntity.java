package com.fraud.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionJpaEntity {

	@Id
	@Column(length = 64)
	private String id;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false, length = 3)
	private String transactionCountry;

	@Column(nullable = false, length = 3)
	private String userCountry;

	@Column(length = 128)
	private String ip;

	@Column(nullable = false)
	private Instant timestamp;

	@Column(length = 128)
	private String userId;
}

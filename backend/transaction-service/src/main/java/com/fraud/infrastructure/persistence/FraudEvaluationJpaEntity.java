package com.fraud.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fraud_evaluations")
@Getter
@Setter
public class FraudEvaluationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 64)
	private String transactionId;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false, length = 3)
	private String transactionCountry;

	@Column(nullable = false, length = 3)
	private String userCountry;

	@Column(length = 128)
	private String ip;

	@Column(nullable = false)
	private Instant transactionTimestamp;

	@Column(nullable = false)
	private boolean suspicious;

	@Column(nullable = false, length = 16)
	private String riskLevel;

	@Column(nullable = false, length = 512)
	private String reasons;

	@Column(nullable = false)
	private Instant evaluatedAt;
}

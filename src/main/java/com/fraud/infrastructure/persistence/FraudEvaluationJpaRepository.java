package com.fraud.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudEvaluationJpaRepository extends JpaRepository<FraudEvaluationJpaEntity, Long> {
}

package com.fraud.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudEvaluationJpaRepository extends JpaRepository<FraudEvaluationJpaEntity, Long> {

	Page<FraudEvaluationJpaEntity> findAllByOrderByEvaluatedAtDesc(Pageable pageable);
}

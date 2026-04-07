package com.fraud.alert.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudAlertJpaRepository extends JpaRepository<FraudAlertEntity, Long> {
    List<FraudAlertEntity> findAllByOrderByTimestampDesc();
}

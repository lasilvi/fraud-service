package com.fraud.config.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdConfigRepository extends JpaRepository<ThresholdConfigEntity, Long> {
}

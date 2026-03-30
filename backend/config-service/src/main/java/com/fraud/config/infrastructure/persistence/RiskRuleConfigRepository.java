package com.fraud.config.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskRuleConfigRepository extends JpaRepository<RiskRuleConfigEntity, Long> {
}

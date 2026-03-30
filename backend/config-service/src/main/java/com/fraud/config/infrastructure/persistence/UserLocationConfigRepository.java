package com.fraud.config.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationConfigRepository extends JpaRepository<UserLocationConfigEntity, Long> {
	Optional<UserLocationConfigEntity> findByUserId(String userId);
}

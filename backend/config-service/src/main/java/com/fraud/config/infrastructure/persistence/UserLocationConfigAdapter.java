package com.fraud.config.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fraud.config.application.port.out.GetUserLocationConfigPort;
import com.fraud.config.application.port.out.SaveUserLocationConfigPort;
import com.fraud.config.domain.model.UserLocationConfig;

@Component
public class UserLocationConfigAdapter implements SaveUserLocationConfigPort, GetUserLocationConfigPort {

	private final UserLocationConfigRepository repository;

	public UserLocationConfigAdapter(UserLocationConfigRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(UserLocationConfig config) {
		UserLocationConfigEntity entity = repository.findByUserId(config.userId())
			.orElse(new UserLocationConfigEntity());
		entity.setUserId(config.userId());
		entity.setUsualCountry(config.usualCountry());
		repository.save(entity);
	}

	@Override
	public Optional<UserLocationConfig> getByUserId(String userId) {
		return repository.findByUserId(userId)
			.map(entity -> new UserLocationConfig(entity.getUserId(), entity.getUsualCountry()));
	}
}

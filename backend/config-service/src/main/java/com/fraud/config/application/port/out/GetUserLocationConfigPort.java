package com.fraud.config.application.port.out;

import java.util.Optional;

import com.fraud.config.domain.model.UserLocationConfig;

public interface GetUserLocationConfigPort {
	Optional<UserLocationConfig> getByUserId(String userId);
}

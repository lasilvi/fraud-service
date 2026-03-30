package com.fraud.config.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.GetUserLocationConfigPort;
import com.fraud.config.domain.model.UserLocationConfig;

@Service
public class GetUserLocationUseCase {

	private final GetUserLocationConfigPort getPort;

	public GetUserLocationUseCase(GetUserLocationConfigPort getPort) {
		this.getPort = getPort;
	}

	public Optional<UserLocationConfig> execute(String userId) {
		return getPort.getByUserId(userId);
	}
}

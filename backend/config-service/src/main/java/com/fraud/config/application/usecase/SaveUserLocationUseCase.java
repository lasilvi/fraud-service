package com.fraud.config.application.usecase;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.SaveUserLocationConfigPort;
import com.fraud.config.domain.model.UserLocationConfig;

@Service
public class SaveUserLocationUseCase {

	private final SaveUserLocationConfigPort savePort;

	public SaveUserLocationUseCase(SaveUserLocationConfigPort savePort) {
		this.savePort = savePort;
	}

	public void execute(UserLocationConfig config) {
		savePort.save(config);
	}
}

package com.fraud.config.application.port.out;

import com.fraud.config.domain.model.UserLocationConfig;

public interface SaveUserLocationConfigPort {
	void save(UserLocationConfig config);
}

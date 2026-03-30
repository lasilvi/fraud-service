package com.fraud.infrastructure.adapter;

import com.fraud.application.port.out.UserLocationProvider;
import com.fraud.infrastructure.client.ConfigServiceClient;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserLocationProviderAdapter implements UserLocationProvider {

    private final ConfigServiceClient configServiceClient;

    public UserLocationProviderAdapter(ConfigServiceClient configServiceClient) {
        this.configServiceClient = configServiceClient;
    }

    @Override
    public Optional<String> getUsualCountry(String userId) {
        return configServiceClient.getUserLocation(userId);
    }
}

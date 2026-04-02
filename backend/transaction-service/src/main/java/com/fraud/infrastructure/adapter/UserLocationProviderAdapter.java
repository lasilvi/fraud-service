package com.fraud.infrastructure.adapter;

import com.fraud.application.port.out.UserLocationProvider;
import com.fraud.infrastructure.client.ConfigServiceClient;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserLocationProviderAdapter implements UserLocationProvider {

    private static final String DEFAULT_USER_ID = "default";

    private final ConfigServiceClient configServiceClient;

    public UserLocationProviderAdapter(ConfigServiceClient configServiceClient) {
        this.configServiceClient = configServiceClient;
    }

    @Override
    public Optional<String> getUsualCountry(String userId) {
        Optional<String> location = configServiceClient.getUserLocation(userId);
        if (location.isEmpty() && !DEFAULT_USER_ID.equals(userId)) {
            return configServiceClient.getUserLocation(DEFAULT_USER_ID);
        }
        return location;
    }
}

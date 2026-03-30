package com.fraud.application.port.out;

import java.util.Optional;

public interface UserLocationProvider {

    Optional<String> getUsualCountry(String userId);
}

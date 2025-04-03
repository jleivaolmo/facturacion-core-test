package com.echevarne.sap.cloud.facturacion.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class ProfileUtils {
    private static Environment environment;

    public ProfileUtils(Environment environment) {
        ProfileUtils.environment = environment;
    }

    public static Environment getEnvironment() {
        if (environment == null) {
            throw new RuntimeException("Environment has not been set yet");
        }
        return environment;
    }

    public static boolean isProfileActive(String profile) {
        if (environment == null) {
            throw new RuntimeException("Environment has not been set yet");
        }
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> (env.equalsIgnoreCase(profile)));
    }

}

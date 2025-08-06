package com.thecatapi.api.config;

import com.thecatapi.api.exceptions.TAFRuntimeException;
import lombok.Getter;

import java.util.Properties;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static java.util.Objects.isNull;

@Getter
public class EnvironmentConfig {

    private static final EnvironmentConfig INSTANCE = new EnvironmentConfig();
    private final String appUrl;
    private final String xApiKey;
    private final int appVersion;
    private final String DEFAULT_ENV = "dev";
    private final String DEFAULT_APP_VERSION = "1";

    private EnvironmentConfig() {
        var env = getProperty("env", DEFAULT_ENV);
        var filePath = "configs/" + env + ".properties";

        var props = loadProperties(filePath);

        this.appUrl = getRequiredProperty(props, "app.url", filePath);
        this.xApiKey = getRequiredProperty(props, "x.api.key", filePath);
        this.appVersion = parseInt(getProperty("apiVersion", DEFAULT_APP_VERSION));
    }

    public static EnvironmentConfig getInstance() {
        return INSTANCE;
    }

    private Properties loadProperties(String path) {
        var props = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream(path)) {
            if (isNull(input)) {
                throw new TAFRuntimeException("Config file not found: " + path);
            }
            props.load(input);
        } catch (Exception e) {
            throw new TAFRuntimeException("Failed to load config file: " + path, e);
        }

        return props;
    }

    private String getRequiredProperty(Properties props, String key, String filePath) {
        var value = props.getProperty(key);
        if (isNull(value) || value.isBlank()) {
            throw new TAFRuntimeException("Missing required property: " + key + " in file: " + filePath);
        }

        return value;
    }
}

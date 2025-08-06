package com.thecatapi.api.core.thecatapi.clients;

import com.thecatapi.api.config.EnvironmentConfig;
import com.thecatapi.api.core.restassuredclient.RestClientFactory;
import com.thecatapi.api.core.restassuredclient.RestClientWrapper;

abstract class AbstractClient {

    private static final EnvironmentConfig environmentConfig = EnvironmentConfig.getInstance();

    public RestClientWrapper getClient() {
        return RestClientFactory.getClient(environmentConfig.getAppUrl(), environmentConfig.getAppVersion());
    }

}

package com.thecatapi.api.core.restassuredclient;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class RestClientWrapper {

    private final RequestSpecification client;

    public RestClientWrapper(RequestSpecification client) {
        this.client = client;
    }

    public RestClientWrapper addContentType(ContentType contentType) {
        client.contentType(contentType);

        return this;
    }

    public RestClientWrapper addEncoderConfig(EncoderConfig encoderConfig) {
        client.config(RestAssured.config().encoderConfig(encoderConfig));

        return this;
    }

    public RestClientWrapper addXApiKey(String xApiKey) {
        if (isNotBlank(xApiKey)) {
            client.header("x-api-key", xApiKey);
        }

        return this;
    }

    public HttpResponseWrapper get(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.get(path, pathParams).then());
    }

    public HttpResponseWrapper post(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.post(path, pathParams).then());
    }

    public HttpResponseWrapper put(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.put(path, pathParams).then());
    }

    public HttpResponseWrapper patch(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.patch(path, pathParams).then());
    }

    public HttpResponseWrapper delete(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.delete(path, pathParams).then());
    }

    public RestClientWrapper body(Object body) {
        client.body(body);

        return this;
    }

    public RestClientWrapper addRequestParams(Map<String, Integer> params) {
        client.params(params);

        return this;
    }

    public RestClientWrapper pathParam(String parameterName, Object parameterValue) {
        client.pathParam(parameterName, parameterValue);

        return this;
    }

    public RestClientWrapper header(String headerName, Object headerValue, Object... pathParams) {
        client.header(headerName, headerValue, pathParams);

        return this;
    }

    public RestClientWrapper queryParam(String parameterName, Object... parameterValues) {
        client.queryParam(parameterName, parameterValues);

        return this;
    }

    public RestClientWrapper formParam(String parameterName, String parameterValues) {
        if (isNotBlank(parameterValues)) {
            client.formParam(parameterName, parameterValues);
        }

        return this;
    }

    public RestClientWrapper multipartParam(String parameterName, File parameterValue, String type) {
        if (!isNull(parameterValue)) {
            client.multiPart(parameterName, parameterValue, type);
        }

        return this;
    }
}

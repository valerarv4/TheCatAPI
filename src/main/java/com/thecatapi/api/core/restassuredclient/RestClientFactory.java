package com.thecatapi.api.core.restassuredclient;

import com.thecatapi.api.utils.loggers.CustomRequestLoggingFilter;
import com.thecatapi.api.utils.loggers.CustomResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RestClientFactory {

    static {
        useRelaxedHTTPSValidation();
    }

    private static RequestSpecification buildBaseSpec(String baseUri) {
        return given()
                .baseUri(baseUri)
                .filters(new CustomRequestLoggingFilter(), new CustomResponseLoggingFilter());
    }

    public static RestClientWrapper getClient(String baseUri) {
        return new RestClientWrapper(buildBaseSpec(baseUri));
    }

    public static RestClientWrapper getClient(String baseUri, int version) {
        var reqSpec = buildBaseSpec(baseUri).basePath("/v" + version);

        return new RestClientWrapper(reqSpec);
    }
}

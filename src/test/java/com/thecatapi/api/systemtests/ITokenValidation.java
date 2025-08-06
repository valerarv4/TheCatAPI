package com.thecatapi.api.systemtests;

import com.thecatapi.api.core.restassuredclient.HttpResponseWrapper;

import java.util.function.Supplier;

import static com.thecatapi.api.utils.ResponseErrorMessages.AUTHENTICATION_ERROR;
import static org.assertj.core.api.Assertions.assertThat;

public interface ITokenValidation {

    default void validateErrorMessage(Supplier<HttpResponseWrapper> httpResponseWrapperSupplier) {
        var responseErrorMessage = httpResponseWrapperSupplier
                .get()
                .getBodyAsString();

        assertThat(responseErrorMessage)
                .as("Incorrect response unauthorized error message")
                .isEqualTo(AUTHENTICATION_ERROR.getResponseErrorMessage());
    }
}

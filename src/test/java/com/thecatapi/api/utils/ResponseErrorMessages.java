package com.thecatapi.api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseErrorMessages {

    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR - you need to send your API Key as the 'x-api-key' header"),
    INVALID_DATA("INVALID_DATA"),
    NOT_FOUND("NOT_FOUND");

    private final String responseErrorMessage;
}

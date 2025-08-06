package com.thecatapi.api.exceptions;

public class HttpException extends RuntimeException {

    public HttpException(String errorMessage) {
        super(errorMessage);
    }

    public HttpException(String errorMessage, AssertionError error) {
        super(errorMessage, error);
    }
}

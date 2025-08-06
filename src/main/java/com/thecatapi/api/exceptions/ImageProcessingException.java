package com.thecatapi.api.exceptions;

public class ImageProcessingException extends RuntimeException {

    public ImageProcessingException(String errorMessage) {
        super(errorMessage);
    }

    public ImageProcessingException(String errorMessage, AssertionError error) {
        super(errorMessage, error);
    }
}

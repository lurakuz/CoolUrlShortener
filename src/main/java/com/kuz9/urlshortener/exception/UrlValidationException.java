package com.kuz9.urlshortener.exception;

public class UrlValidationException extends RuntimeException {

    public UrlValidationException(String message) {
        super(message);
    }
}

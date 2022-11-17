package com.intervale.test.library.exception;

public class NewspaperNotFoundException extends RuntimeException {
    public NewspaperNotFoundException(String message) {
        super(message);
    }
}

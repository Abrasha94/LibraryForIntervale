package com.intervale.test.library.exception;

public class MagazineNotFoundException extends RuntimeException {
    public MagazineNotFoundException(String message) {
        super(message);
    }
}

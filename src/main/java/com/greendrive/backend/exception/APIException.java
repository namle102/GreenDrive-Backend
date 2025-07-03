package com.greendrive.backend.exception;

public class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }
}

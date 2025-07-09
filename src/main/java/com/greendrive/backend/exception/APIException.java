package com.greendrive.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class APIException extends RuntimeException {

    private final HttpStatus status;

    public APIException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

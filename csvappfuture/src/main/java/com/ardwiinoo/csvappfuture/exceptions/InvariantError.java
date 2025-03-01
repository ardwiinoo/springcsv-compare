package com.ardwiinoo.csvappfuture.exceptions;

import org.springframework.http.HttpStatus;

public class InvariantError extends ApiError {

    public InvariantError(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

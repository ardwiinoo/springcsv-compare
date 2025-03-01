package com.ardwiinoo.csvappfuture.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Data
public class ApiError extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus statusCode;

    public ApiError(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}

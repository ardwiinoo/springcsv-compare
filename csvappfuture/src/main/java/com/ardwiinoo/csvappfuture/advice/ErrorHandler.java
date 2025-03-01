package com.ardwiinoo.csvappfuture.advice;

import com.ardwiinoo.csvappfuture.exceptions.ApiError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ApiError.class)
    public ApiError handleApiError(ApiError ex) {
        return ex;
    }
}

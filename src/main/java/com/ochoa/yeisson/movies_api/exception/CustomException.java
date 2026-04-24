package com.ochoa.yeisson.movies_api.exception;

import com.ochoa.yeisson.movies_api.enums.ErrorType;

public class CustomException extends RuntimeException {
    private final ErrorType errorType;

    public CustomException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}

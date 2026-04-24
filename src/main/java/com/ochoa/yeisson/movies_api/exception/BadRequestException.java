package com.ochoa.yeisson.movies_api.exception;

import com.ochoa.yeisson.movies_api.enums.ErrorType;

public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(message, ErrorType.BAD_REQUEST);
    }
}

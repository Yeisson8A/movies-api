package com.ochoa.yeisson.movies_api.exception;

import com.ochoa.yeisson.movies_api.enums.ErrorType;

public class NotFoundException extends CustomException {
    public NotFoundException(String message) {
        super(message, ErrorType.NOT_FOUND);
    }
}

package com.ochoa.yeisson.movies_api.validation;

import com.ochoa.yeisson.movies_api.exception.BadRequestException;

public class ValidationUtils {
    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(fieldName + " is required");
        }
    }

    public static void requireMinValue(Integer value, int min, String fieldName) {
        if (value != null && value < min) {
            throw new BadRequestException(fieldName + " must be >= " + min);
        }
    }

    public static void requireRange(Integer value, int min, int max, String fieldName) {
        if (value == null || value < min || value > max) {
            throw new BadRequestException(
                    fieldName + " must be between " + min + " and " + max
            );
        }
    }
}

package com.ochoa.yeisson.movies_api.validation;

public class MovieValidator {
    public static void validate(String title, Integer releaseYear) {
        ValidationUtils.requireNonBlank(title, "Title");
        ValidationUtils.requireMinValue(releaseYear, 1888, "Release year");
    }
}

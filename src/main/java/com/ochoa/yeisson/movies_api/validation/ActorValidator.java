package com.ochoa.yeisson.movies_api.validation;

public class ActorValidator {
    public static void validateName(String name) {
        ValidationUtils.requireNonBlank(name, "Actor name");
    }
}

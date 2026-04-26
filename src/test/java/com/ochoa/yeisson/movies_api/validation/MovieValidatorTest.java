package com.ochoa.yeisson.movies_api.validation;

import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieValidatorTest {
    // ========================= // VALID CASE // =========================
    @Test
    void validate_shouldPass_whenValidData() {
        assertDoesNotThrow(() -> MovieValidator.validate("Inception", 2010));
    }

    // ========================= // TITLE VALIDATION // =========================
    @Test
    void validate_shouldThrow_whenTitleIsNull() {
        assertThrows(BadRequestException.class, () -> MovieValidator.validate(null, 2010));
    }

    @Test
    void validate_shouldThrow_whenTitleIsEmpty() {
        assertThrows(BadRequestException.class, () -> MovieValidator.validate("", 2010));
    }

    @Test
    void validate_shouldThrow_whenTitleIsBlank() {
        assertThrows(BadRequestException.class, () -> MovieValidator.validate(" ", 2010));
    }

    // ========================= // RELEASE YEAR VALIDATION // =========================
    @Test
    void validate_shouldThrow_whenYearIsNull() {
        assertThrows(BadRequestException.class, () -> MovieValidator.validate("Inception", null));
    }

    @Test
    void validate_shouldThrow_whenYearIsLessThanMinimum() {
        assertThrows(BadRequestException.class, () -> MovieValidator.validate("Old Movie", 1800));
    }

    // ========================= // EDGE CASE // =========================
    @Test
    void validate_shouldPass_whenYearIsMinimumAllowed() {
        assertDoesNotThrow(() -> MovieValidator.validate("First Film", 1888));
    }
}

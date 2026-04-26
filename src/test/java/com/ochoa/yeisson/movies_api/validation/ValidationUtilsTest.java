package com.ochoa.yeisson.movies_api.validation;

import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilsTest {
    // ========================= // requireNonBlank // =========================
    @Test
    void requireNonBlank_shouldPass_whenValid() {
        assertDoesNotThrow(() -> ValidationUtils.requireNonBlank("Test", "Field"));
    }

    @Test
    void requireNonBlank_shouldThrow_whenNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireNonBlank(null, "Field"));
        assertEquals("Field is required", ex.getMessage());
    }

    @Test
    void requireNonBlank_shouldThrow_whenEmpty() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireNonBlank("", "Field"));
        assertEquals("Field is required", ex.getMessage());
    }

    @Test
    void requireNonBlank_shouldThrow_whenBlank() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireNonBlank(" ", "Field"));
        assertEquals("Field is required", ex.getMessage());
    }

    // ========================= // requireMinValue // =========================
    @Test
    void requireMinValue_shouldPass_whenValid() {
        assertDoesNotThrow(() -> ValidationUtils.requireMinValue(2000, 1888, "Year"));
    }

    @Test
    void requireMinValue_shouldThrow_whenNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireMinValue(null, 1888, "Year"));
        assertEquals("Year is required", ex.getMessage());
    }

    @Test
    void requireMinValue_shouldThrow_whenLessThanMin() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireMinValue(1800, 1888, "Year"));
        assertEquals("Year must be >= 1888", ex.getMessage());
    }

    @Test
    void requireMinValue_shouldPass_whenEqualToMin() {
        assertDoesNotThrow(() -> ValidationUtils.requireMinValue(1888, 1888, "Year"));
    }

    // ========================= // requireRange // =========================
    @Test
    void requireRange_shouldPass_whenWithinRange() {
        assertDoesNotThrow(() -> ValidationUtils.requireRange(3, 1, 5, "Rating"));
    }

    @Test
    void requireRange_shouldThrow_whenNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireRange(null, 1, 5, "Rating"));
        assertEquals("Rating must be between 1 and 5", ex.getMessage());
    }

    @Test
    void requireRange_shouldThrow_whenLessThanMin() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireRange(0, 1, 5, "Rating"));
        assertEquals("Rating must be between 1 and 5", ex.getMessage());
    }

    @Test
    void requireRange_shouldThrow_whenGreaterThanMax() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> ValidationUtils.requireRange(6, 1, 5, "Rating"));
        assertEquals("Rating must be between 1 and 5", ex.getMessage());
    }

    @Test
    void requireRange_shouldPass_whenAtBoundaries() {
        assertDoesNotThrow(() -> ValidationUtils.requireRange(1, 1, 5, "Rating"));
        assertDoesNotThrow(() -> ValidationUtils.requireRange(5, 1, 5, "Rating"));
    }
}

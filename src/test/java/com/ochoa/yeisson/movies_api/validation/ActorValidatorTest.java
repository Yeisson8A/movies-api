package com.ochoa.yeisson.movies_api.validation;

import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActorValidatorTest {
    // ========================= // VALID CASES // =========================
    @Test
    void validateName_shouldPass_whenValidName() {
        assertDoesNotThrow(() -> ActorValidator.validateName("Leonardo DiCaprio"));
    }

    @Test
    void validateName_shouldPass_whenNameHasSpacesAround() {
        assertDoesNotThrow(() -> ActorValidator.validateName(" Brad Pitt "));
    }

    // ========================= // INVALID CASES // =========================
    @Test
    void validateName_shouldThrow_whenNull() {
        assertThrows(BadRequestException.class, () -> ActorValidator.validateName(null));
    }

    @Test
    void validateName_shouldThrow_whenEmpty() {
        assertThrows(BadRequestException.class, () -> ActorValidator.validateName(""));
    }

    @Test
    void validateName_shouldThrow_whenBlank() {
        assertThrows(BadRequestException.class, () -> ActorValidator.validateName(" "));
    }
}

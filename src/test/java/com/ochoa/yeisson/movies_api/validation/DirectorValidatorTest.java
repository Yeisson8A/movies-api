package com.ochoa.yeisson.movies_api.validation;

import com.ochoa.yeisson.movies_api.entities.Director;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.repository.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DirectorValidatorTest {
    @Mock
    private DirectorRepository directorRepository;
    @InjectMocks
    private DirectorValidator validator;

    // ========================= // VALID CASE // =========================
    @Test
    void validateName_shouldPass_whenValidAndNotDuplicate() {
        String name = "Nolan";

        when(directorRepository.findByNameIgnoreCase(name)) .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> validator.validateName(name));
        verify(directorRepository).findByNameIgnoreCase(name);
    }

    // ========================= // NULL / BLANK // =========================
    @Test
    void validateName_shouldThrow_whenNull() {
        assertThrows(BadRequestException.class, () -> validator.validateName(null));
        verify(directorRepository, never()).findByNameIgnoreCase(any());
    }

    @Test
    void validateName_shouldThrow_whenEmpty() {
        assertThrows(BadRequestException.class, () -> validator.validateName(""));
        verify(directorRepository, never()).findByNameIgnoreCase(any());
    }

    @Test
    void validateName_shouldThrow_whenBlank() {
        assertThrows(BadRequestException.class, () -> validator.validateName(" "));
        verify(directorRepository, never()).findByNameIgnoreCase(any());
    }

    // ========================= // DUPLICATE // =========================
    @Test
    void validateName_shouldThrow_whenDirectorExists() {
        String name = "Nolan";

        when(directorRepository.findByNameIgnoreCase(name)) .thenReturn(Optional.of(new Director()));
        assertThrows(BadRequestException.class, () -> validator.validateName(name));
        verify(directorRepository).findByNameIgnoreCase(name);
    }
}

package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DirectorMutationResolverTest {
    @Mock
    private DirectorService directorService;
    @InjectMocks
    private DirectorMutationResolver resolver;

    // ========================= // CREATE // =========================
    @Test
    void createDirector_shouldReturnDirector() {
        DirectorDTO dto = new DirectorDTO();

        when(directorService.createDirector("Nolan")).thenReturn(dto);

        DirectorDTO result = resolver.createDirector("Nolan");

        assertNotNull(result);
        verify(directorService).createDirector("Nolan");
    }

    @Test
    void createDirector_shouldThrowBadRequest() {
        when(directorService.createDirector("")) .thenThrow(new BadRequestException("Invalid name"));
        assertThrows(BadRequestException.class, () -> resolver.createDirector(""));
        verify(directorService).createDirector("");
    }

    // ========================= // UPDATE // =========================
    @Test
    void updateDirector_shouldReturnDirector() {
        DirectorDTO dto = new DirectorDTO();

        when(directorService.updateDirector(1L, "Spielberg")).thenReturn(dto);

        DirectorDTO result = resolver.updateDirector(1L, "Spielberg");

        assertNotNull(result);
        verify(directorService).updateDirector(1L, "Spielberg");
    }

    @Test
    void updateDirector_shouldThrowNotFound() {
        when(directorService.updateDirector(1L, "Test")) .thenThrow(new NotFoundException("Director not found"));
        assertThrows(NotFoundException.class, () -> resolver.updateDirector(1L, "Test"));
        verify(directorService).updateDirector(1L, "Test");
    }

    // ========================= // DELETE // =========================
    @Test
    void deleteDirector_shouldReturnTrue() {
        doNothing().when(directorService).deleteDirector(1L);

        Boolean result = resolver.deleteDirector(1L);

        assertTrue(result);
        verify(directorService).deleteDirector(1L);
    }

    @Test
    void deleteDirector_shouldThrowNotFound() {
        doThrow(new NotFoundException("Director not found")) .when(directorService).deleteDirector(1L);
        assertThrows(NotFoundException.class, () -> resolver.deleteDirector(1L));
        verify(directorService).deleteDirector(1L);
    }
}

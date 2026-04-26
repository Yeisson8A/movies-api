package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorQueryResolverTest {
    @Mock
    private DirectorService directorService;
    @InjectMocks
    private DirectorQueryResolver resolver;

    // ========================= // GET ALL // =========================
    @Test
    void directors_shouldReturnList() {
        List<DirectorDTO> directors = List.of(new DirectorDTO(), new DirectorDTO());

        when(directorService.getAllDirectors()).thenReturn(directors);

        List<DirectorDTO> result = resolver.directors();

        assertEquals(2, result.size());
        verify(directorService).getAllDirectors();
    }

    // ========================= // GET BY ID // =========================
    @Test
    void directorById_shouldReturnDirector() {
        DirectorDTO dto = new DirectorDTO();

        when(directorService.getDirectorById(1L)).thenReturn(dto);

        DirectorDTO result = resolver.directorById(1L);

        assertNotNull(result);
        verify(directorService).getDirectorById(1L);
    }

    @Test
    void directorById_shouldThrowNotFound() {
        when(directorService.getDirectorById(1L)) .thenThrow(new NotFoundException("Director not found"));
        assertThrows(NotFoundException.class, () -> resolver.directorById(1L));
        verify(directorService).getDirectorById(1L);
    }
}

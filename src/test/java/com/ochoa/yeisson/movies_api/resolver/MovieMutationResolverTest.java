package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.dto.MovieInput;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieMutationResolverTest {
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieMutationResolver resolver;

    // ========================= // CREATE // =========================
    @Test
    void createMovie_shouldReturnMovie() {
        MovieInput input = new MovieInput();
        MovieDTO dto = new MovieDTO();

        when(movieService.createMovie(input)).thenReturn(dto);

        MovieDTO result = resolver.createMovie(input);

        assertNotNull(result);
        verify(movieService).createMovie(input);
    }

    @Test
    void createMovie_shouldThrowBadRequest() {
        MovieInput input = new MovieInput();

        when(movieService.createMovie(input)) .thenThrow(new BadRequestException("Invalid data"));
        assertThrows(BadRequestException.class, () -> resolver.createMovie(input));
        verify(movieService).createMovie(input);
    }

    // ========================= // UPDATE // =========================
    @Test
    void updateMovie_shouldReturnMovie() {
        MovieInput input = new MovieInput();
        MovieDTO dto = new MovieDTO();

        when(movieService.updateMovie(1L, input)).thenReturn(dto);

        MovieDTO result = resolver.updateMovie(1L, input);

        assertNotNull(result);
        verify(movieService).updateMovie(1L, input);
    }

    @Test
    void updateMovie_shouldThrowNotFound() {
        when(movieService.updateMovie(1L, new MovieInput())) .thenThrow(new NotFoundException("Movie not found"));
        assertThrows(NotFoundException.class, () -> resolver.updateMovie(1L, new MovieInput()));
        verify(movieService).updateMovie(eq(1L), any());
    }

    // ========================= // DELETE // =========================
    @Test
    void deleteMovie_shouldReturnTrue() {
        doNothing().when(movieService).deleteMovie(1L);

        Boolean result = resolver.deleteMovie(1L);

        assertTrue(result);
        verify(movieService).deleteMovie(1L);
    }

    @Test
    void deleteMovie_shouldThrowNotFound() {
        doThrow(new NotFoundException("Movie not found")) .when(movieService).deleteMovie(1L);
        assertThrows(NotFoundException.class, () -> resolver.deleteMovie(1L));
        verify(movieService).deleteMovie(1L);
    }

    // ========================= // ASSIGN DIRECTOR // =========================
    @Test
    void assignDirector_shouldReturnMovie() {
        MovieDTO dto = new MovieDTO();

        when(movieService.assignDirector(1L, 2L)).thenReturn(dto);

        MovieDTO result = resolver.assignDirector(1L, 2L);

        assertNotNull(result);
        verify(movieService).assignDirector(1L, 2L);
    }

    @Test
    void assignDirector_shouldThrowNotFound() {
        when(movieService.assignDirector(1L, 2L)) .thenThrow(new NotFoundException("Director not found"));
        assertThrows(NotFoundException.class, () -> resolver.assignDirector(1L, 2L));
    }

    // ========================= // ADD ACTORS // =========================
    @Test
    void addActors_shouldReturnMovie() {
        MovieDTO dto = new MovieDTO();

        when(movieService.addActors(1L, List.of(1L, 2L))).thenReturn(dto);

        MovieDTO result = resolver.addActors(1L, List.of(1L, 2L));

        assertNotNull(result);
        verify(movieService).addActors(1L, List.of(1L, 2L));
    }

    @Test
    void addActors_shouldThrowBadRequest() {
        when(movieService.addActors(1L, List.of())) .thenThrow(new BadRequestException("Invalid actors"));
        assertThrows(BadRequestException.class, () -> resolver.addActors(1L, List.of()));
    }

    // ========================= // REMOVE ACTOR // =========================
    @Test
    void removeActor_shouldReturnMovie() {
        MovieDTO dto = new MovieDTO();

        when(movieService.removeActor(1L, 2L)).thenReturn(dto);

        MovieDTO result = resolver.removeActor(1L, 2L);

        assertNotNull(result);
        verify(movieService).removeActor(1L, 2L);
    }

    @Test
    void removeActor_shouldThrowNotFound() {
        when(movieService.removeActor(1L, 2L)) .thenThrow(new NotFoundException("Movie not found"));
        assertThrows(NotFoundException.class, () -> resolver.removeActor(1L, 2L));
    }
}

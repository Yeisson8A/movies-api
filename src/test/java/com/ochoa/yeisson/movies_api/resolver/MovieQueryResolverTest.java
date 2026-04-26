package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.MovieService;
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
public class MovieQueryResolverTest {
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieQueryResolver resolver;

    // ========================= // GET ALL // =========================
    @Test
    void movies_shouldReturnList() {
        List<MovieDTO> movies = List.of(new MovieDTO(), new MovieDTO());

        when(movieService.getAllMovies()).thenReturn(movies);

        List<MovieDTO> result = resolver.movies();

        assertEquals(2, result.size());
        verify(movieService).getAllMovies();
    }

    // ========================= // GET BY ID // =========================
    @Test
    void movieById_shouldReturnMovie() {
        MovieDTO dto = new MovieDTO();

        when(movieService.getMovieById(1L)).thenReturn(dto);

        MovieDTO result = resolver.movieById(1L);

        assertNotNull(result);
        verify(movieService).getMovieById(1L);
    }

    @Test
    void movieById_shouldThrowNotFound() {
        when(movieService.getMovieById(1L)) .thenThrow(new NotFoundException("Movie not found"));
        assertThrows(NotFoundException.class, () -> resolver.movieById(1L));
        verify(movieService).getMovieById(1L);
    }
}

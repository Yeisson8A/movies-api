package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.dto.MovieInput;
import com.ochoa.yeisson.movies_api.entities.Actor;
import com.ochoa.yeisson.movies_api.entities.Director;
import com.ochoa.yeisson.movies_api.entities.Movie;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.mapper.MovieInputMapper;
import com.ochoa.yeisson.movies_api.mapper.MovieMapper;
import com.ochoa.yeisson.movies_api.repository.ActorRepository;
import com.ochoa.yeisson.movies_api.repository.DirectorRepository;
import com.ochoa.yeisson.movies_api.repository.MovieRepository;
import com.ochoa.yeisson.movies_api.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock private MovieRepository movieRepository;
    @Mock private DirectorRepository directorRepository;
    @Mock
    private ActorRepository actorRepository;
    @Mock private MovieMapper movieMapper;
    @Mock private MovieInputMapper movieInputMapper;
    @InjectMocks
    private MovieServiceImpl movieService;

    // ========================= // GET ALL // =========================
    @Test
    void getAllMovies_shouldReturnList() {
        List<Movie> movies = List.of(new Movie(), new Movie());

        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDTO(any())).thenReturn(new MovieDTO());

        List<MovieDTO> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        verify(movieRepository).findAll();
    }

    // ========================= // GET BY ID // =========================
    @Test
    void getMovieById_shouldReturnMovie() {
        Movie movie = new Movie();

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.getMovieById(1L);

        assertNotNull(result);
    }

    @Test
    void getMovieById_shouldThrowNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> movieService.getMovieById(1L));
    }

    // ========================= // CREATE // =========================
    @Test
    void createMovie_shouldSaveWithDirectorAndActors() {
        MovieInput input = new MovieInput();
        input.setTitle("Movie 1");
        input.setReleaseYear(2021);
        input.setDirectorId(1L);
        input.setActorIds(List.of(1L, 2L));
        Movie movie = new Movie();
        Director director = new Director();
        List<Actor> actors = List.of(new Actor(), new Actor());

        when(movieInputMapper.toEntity(input)).thenReturn(movie);
        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        when(actorRepository.findAllById(input.getActorIds())).thenReturn(actors);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.createMovie(input);

        assertNotNull(result);
        assertEquals(director, movie.getDirector());
        assertEquals(actors, movie.getActors());
    }

    @Test
    void createMovie_shouldThrowIfDirectorNotFound() {
        MovieInput input = new MovieInput();
        input.setTitle("Movie 1");
        input.setReleaseYear(2021);
        input.setDirectorId(1L);

        when(movieInputMapper.toEntity(input)).thenReturn(new Movie());
        when(directorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> movieService.createMovie(input));
    }

    @Test
    void createMovie_shouldThrowIfActorsNotFound() {
        MovieInput input = new MovieInput();
        input.setTitle("Movie 1");
        input.setReleaseYear(2021);
        input.setActorIds(List.of(1L, 2L));

        when(movieInputMapper.toEntity(input)).thenReturn(new Movie());
        when(actorRepository.findAllById(input.getActorIds())) .thenReturn(List.of(new Actor()));
        assertThrows(BadRequestException.class, () -> movieService.createMovie(input));
    }

    // ========================= // UPDATE // =========================
    @Test
    void updateMovie_shouldUpdateFields() {
        Movie existing = new Movie();
        MovieInput input = new MovieInput();
        input.setTitle("New Title");
        input.setReleaseYear(2020);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(movieRepository.save(existing)).thenReturn(existing);
        when(movieMapper.toDTO(existing)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.updateMovie(1L, input);

        assertEquals("New Title", existing.getTitle());
        assertEquals(2020, existing.getReleaseYear());
        assertNotNull(result);
    }

    @Test
    void updateMovie_shouldThrowNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> movieService.updateMovie(1L, new MovieInput()));
    }

    // ========================= // DELETE // =========================
    @Test
    void deleteMovie_shouldDelete() {
        when(movieRepository.existsById(1L)).thenReturn(true);

        movieService.deleteMovie(1L);

        verify(movieRepository).deleteById(1L);
    }

    @Test
    void deleteMovie_shouldThrowNotFound() {
        when(movieRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> movieService.deleteMovie(1L));
    }

    // ========================= // ASSIGN DIRECTOR // =========================
    @Test
    void assignDirector_shouldAssign() {
        Movie movie = new Movie();
        Director director = new Director();
        director.setId(2L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(directorRepository.findById(2L)).thenReturn(Optional.of(director));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.assignDirector(1L, 2L);

        assertEquals(director, movie.getDirector());
        assertNotNull(result);
    }

    @Test
    void assignDirector_shouldNotOverwriteSameDirector() {
        Director director = new Director();
        director.setId(2L);
        Movie movie = new Movie();
        movie.setDirector(director);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(directorRepository.findById(2L)).thenReturn(Optional.of(director));
        when(movieMapper.toDTO(movie)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.assignDirector(1L, 2L);

        verify(movieRepository, never()).save(any());
        assertNotNull(result);
    }

    // ========================= // ADD ACTORS // =========================
    @Test void addActors_shouldAddActors() {
        Movie movie = new Movie();
        movie.setActors(new ArrayList<>());
        List<Actor> actors = List.of(new Actor(), new Actor());

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(actorRepository.findByIdIn(List.of(1L, 2L))).thenReturn(actors);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.addActors(1L, List.of(1L, 2L));

        assertEquals(2, movie.getActors().size());
        assertNotNull(result);
    }

    @Test
    void addActors_shouldThrowIfEmpty() {
        assertThrows(BadRequestException.class, () -> movieService.addActors(1L, List.of()));
    }

    @Test
    void addActors_shouldThrowIfNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(new Movie()));
        when(actorRepository.findByIdIn(List.of(1L))) .thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> movieService.addActors(1L, List.of(1L)));
    }

    // ========================= // REMOVE ACTOR // =========================
    @Test
    void removeActor_shouldRemove() {
        Actor actor = new Actor();
        actor.setId(1L);
        Movie movie = new Movie();
        movie.setActors(new ArrayList<>(List.of(actor)));

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(new MovieDTO());

        MovieDTO result = movieService.removeActor(1L, 1L);

        assertTrue(movie.getActors().isEmpty());
        assertNotNull(result);
    }
}

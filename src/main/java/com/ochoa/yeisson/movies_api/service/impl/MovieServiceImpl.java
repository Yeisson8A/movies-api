package com.ochoa.yeisson.movies_api.service.impl;

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
import com.ochoa.yeisson.movies_api.service.MovieService;
import com.ochoa.yeisson.movies_api.validation.MovieValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final MovieMapper movieMapper;
    private final MovieInputMapper movieInputMapper;

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        return movieMapper.toDTO(movie);
    }

    @Override
    public MovieDTO createMovie(MovieInput input) {
        MovieValidator.validate(input.getTitle(), input.getReleaseYear());

        Movie movie = movieInputMapper.toEntity(input);

        // relaciones
        if (input.getDirectorId() != null) {
            Director director = directorRepository.findById(input.getDirectorId())
                    .orElseThrow(() -> new NotFoundException("Director not found"));
            movie.setDirector(director);
        }

        if (input.getActorIds() != null && !input.getActorIds().isEmpty()) {
            List<Actor> actors = actorRepository.findAllById(input.getActorIds());

            if (actors.size() != input.getActorIds().size()) {
                throw new BadRequestException("Some actors not found");
            }

            movie.setActors(actors);
        }

        Movie saved = movieRepository.save(movie);

        return movieMapper.toDTO(saved);
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieInput input) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        MovieValidator.validate(input.getTitle(), input.getReleaseYear());

        existing.setTitle(input.getTitle());
        existing.setReleaseYear(input.getReleaseYear());

        Movie updated = movieRepository.save(existing);

        return movieMapper.toDTO(updated);
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie not found");
        }

        movieRepository.deleteById(id);
    }

    @Override
    public MovieDTO assignDirector(Long movieId, Long directorId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new NotFoundException("Director not found"));

        // evitar sobreescribir
        if (movie.getDirector() != null &&
                movie.getDirector().getId().equals(directorId)) {
            return movieMapper.toDTO(movie);
        }

        movie.setDirector(director);

        Movie saved = movieRepository.save(movie);

        return movieMapper.toDTO(saved);
    }

    @Override
    public MovieDTO addActors(Long movieId, List<Long> actorIds) {
        if (actorIds == null || actorIds.isEmpty()) {
            throw new BadRequestException("Actor IDs are required");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        List<Actor> actors = actorRepository.findByIdIn(actorIds);

        if (actors.size() != actorIds.size()) {
            throw new NotFoundException("Some actors not found");
        }

        // evitar duplicados
        List<Actor> currentActors = movie.getActors();

        if (currentActors == null) {
            movie.setActors(actors);
        } else {
            List<Long> existingIds = currentActors.stream()
                    .map(Actor::getId)
                    .toList();

            actors.forEach(actor -> {
                if (!existingIds.contains(actor.getId())) {
                    currentActors.add(actor);
                }
            });
        }

        Movie saved = movieRepository.save(movie);

        return movieMapper.toDTO(saved);
    }

    @Override
    public MovieDTO removeActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        movie.getActors().removeIf(a -> a.getId().equals(actorId));

        return movieMapper.toDTO(movieRepository.save(movie));
    }
}

package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.dto.MovieInput;
import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(Long id);

    MovieDTO createMovie(MovieInput input);

    MovieDTO updateMovie(Long id, MovieInput input);

    void deleteMovie(Long id);

    MovieDTO assignDirector(Long movieId, Long directorId);

    MovieDTO addActors(Long movieId, List<Long> actorIds);

    MovieDTO removeActor(Long movieId, Long actorId);
}

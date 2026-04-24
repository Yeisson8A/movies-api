package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieQueryResolver {
    private final MovieService movieService;

    @QueryMapping
    public List<MovieDTO> movies() {
        return movieService.getAllMovies();
    }

    @QueryMapping
    public MovieDTO movieById(Long id) {
        return movieService.getMovieById(id);
    }
}

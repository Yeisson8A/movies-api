package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.dto.MovieInput;
import com.ochoa.yeisson.movies_api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieMutationResolver {
    private final MovieService movieService;

    @MutationMapping
    public MovieDTO createMovie(@Argument MovieInput input) {
        return movieService.createMovie(input);
    }

    @MutationMapping
    public MovieDTO updateMovie(@Argument Long id, @Argument MovieInput input) {
        return movieService.updateMovie(id, input);
    }

    @MutationMapping
    public Boolean deleteMovie(@Argument Long id) {
        movieService.deleteMovie(id);
        return true;
    }

    @MutationMapping
    public MovieDTO assignDirector(@Argument Long movieId,
                                   @Argument Long directorId) {
        return movieService.assignDirector(movieId, directorId);
    }

    @MutationMapping
    public MovieDTO addActors(@Argument Long movieId,
                              @Argument List<Long> actorIds) {
        return movieService.addActors(movieId, actorIds);
    }

    @MutationMapping
    public MovieDTO removeActor(@Argument Long movieId, @Argument Long actorId) {
        return movieService.removeActor(movieId, actorId);
    }
}

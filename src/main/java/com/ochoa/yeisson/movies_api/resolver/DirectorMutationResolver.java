package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DirectorMutationResolver {
    private final DirectorService directorService;

    @MutationMapping
    public DirectorDTO createDirector(@Argument String name) {
        return directorService.createDirector(name);
    }

    @MutationMapping
    public DirectorDTO updateDirector(@Argument Long id, @Argument String name) {
        return directorService.updateDirector(id, name);
    }

    @MutationMapping
    public Boolean deleteDirector(@Argument Long id) {
        directorService.deleteDirector(id);
        return true;
    }
}

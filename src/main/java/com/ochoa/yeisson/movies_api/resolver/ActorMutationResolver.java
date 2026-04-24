package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ActorMutationResolver {
    private final ActorService actorService;

    @MutationMapping
    public ActorDTO createActor(@Argument String name) {
        return actorService.createActor(name);
    }

    @MutationMapping
    public ActorDTO updateActor(@Argument Long id, @Argument String name) {
        return actorService.updateActor(id, name);
    }

    @MutationMapping
    public Boolean deleteActor(@Argument Long id) {
        actorService.deleteActor(id);
        return true;
    }
}

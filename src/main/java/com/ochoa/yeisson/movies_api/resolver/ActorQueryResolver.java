package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ActorQueryResolver {
    private final ActorService actorService;

    @QueryMapping
    public List<ActorDTO> actors() {
        return actorService.getAllActors();
    }

    @QueryMapping
    public ActorDTO actorById(Long id) {
        return actorService.getActorById(id);
    }
}

package com.ochoa.yeisson.movies_api.repository;

import com.ochoa.yeisson.movies_api.entities.Actor;
import java.util.Collection;
import java.util.List;

public interface ActorRepository extends BaseRepository<Actor, Long> {
    List<Actor> findByNameContainingIgnoreCase(String name);

    List<Actor> findByMovies_IdIn(Collection<Long> movieIds);

    List<Actor> findByIdIn(List<Long> actorIds);
}

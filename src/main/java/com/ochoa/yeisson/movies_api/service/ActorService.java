package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import java.util.List;

public interface ActorService {
    List<ActorDTO> getAllActors();

    ActorDTO getActorById(Long id);

    ActorDTO createActor(String name);

    ActorDTO updateActor(Long id, String name);

    void deleteActor(Long id);
}

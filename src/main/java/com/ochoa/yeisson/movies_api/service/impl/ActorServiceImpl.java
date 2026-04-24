package com.ochoa.yeisson.movies_api.service.impl;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.entities.Actor;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.mapper.ActorMapper;
import com.ochoa.yeisson.movies_api.repository.ActorRepository;
import com.ochoa.yeisson.movies_api.service.ActorService;
import com.ochoa.yeisson.movies_api.validation.ActorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    public List<ActorDTO> getAllActors() {
        return actorMapper.toDTOList(actorRepository.findAll());
    }

    @Override
    public ActorDTO getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actor not found"));

        return actorMapper.toDTO(actor);
    }

    @Override
    public ActorDTO createActor(String name) {
        ActorValidator.validateName(name);

        Actor actor = Actor.builder()
                .name(name.trim())
                .build();

        Actor saved = actorRepository.save(actor);

        return actorMapper.toDTO(saved);
    }

    @Override
    public ActorDTO updateActor(Long id, String name) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actor not found"));

        ActorValidator.validateName(name);

        actor.setName(name.trim());

        return actorMapper.toDTO(actorRepository.save(actor));
    }

    @Override
    public void deleteActor(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actor not found"));

        // evitar borrar si está asociado a películas
        if (actor.getMovies() != null && !actor.getMovies().isEmpty()) {
            throw new BadRequestException("Cannot delete actor assigned to movies");
        }

        actorRepository.delete(actor);
    }
}

package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.entities.Actor;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.mapper.ActorMapper;
import com.ochoa.yeisson.movies_api.repository.ActorRepository;
import com.ochoa.yeisson.movies_api.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;
    @Mock private ActorMapper actorMapper;
    @InjectMocks
    private ActorServiceImpl actorService;

    // ========================= // GET ALL // =========================
    @Test
    void getAllActors_shouldReturnList() {
        List<Actor> actors = List.of(new Actor(), new Actor());
        List<ActorDTO> dtos = List.of(new ActorDTO(), new ActorDTO());

        when(actorRepository.findAll()).thenReturn(actors);
        when(actorMapper.toDTOList(actors)).thenReturn(dtos);

        List<ActorDTO> result = actorService.getAllActors();

        assertEquals(2, result.size());

        verify(actorRepository).findAll();
        verify(actorMapper).toDTOList(actors);
    }

    // ========================= // GET BY ID // =========================
    @Test
    void getActorById_shouldReturnActor() {
        Actor actor = new Actor();
        ActorDTO dto = new ActorDTO();

        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(actorMapper.toDTO(actor)).thenReturn(dto);

        ActorDTO result = actorService.getActorById(1L);

        assertNotNull(result); verify(actorRepository).findById(1L);
    }

    @Test
    void getActorById_shouldThrowNotFound() {
        when(actorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> actorService.getActorById(1L));
    }

    // ========================= // CREATE // =========================
    @Test
    void createActor_shouldSaveActor() {
        Actor actor = Actor.builder().name("Leo").build();
        Actor saved = Actor.builder().name("Leo").build();
        ActorDTO dto = new ActorDTO();

        when(actorRepository.save(any(Actor.class))).thenReturn(saved);
        when(actorMapper.toDTO(saved)).thenReturn(dto);

        ActorDTO result = actorService.createActor("Leo");

        assertNotNull(result);
        verify(actorRepository).save(any(Actor.class));
    }

    // ========================= // UPDATE // =========================
    @Test
    void updateActor_shouldUpdateName() {
        Actor actor = new Actor();
        ActorDTO dto = new ActorDTO();

        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(actorRepository.save(actor)).thenReturn(actor);
        when(actorMapper.toDTO(actor)).thenReturn(dto);

        ActorDTO result = actorService.updateActor(1L, "Brad Pitt");

        assertNotNull(result); verify(actorRepository).save(actor);
    }

    @Test
    void updateActor_shouldThrowNotFound() {
        when(actorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> actorService.updateActor(1L, "Test"));
    }

    // ========================= // DELETE // =========================
    @Test
    void deleteActor_shouldDelete() {
        Actor actor = new Actor();
        actor.setMovies(Collections.emptyList());

        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        actorService.deleteActor(1L); verify(actorRepository).delete(actor);
    }

    @Test
    void deleteActor_shouldThrowIfAssignedToMovies() {
        Actor actor = new Actor();
        actor.setMovies(List.of(new com.ochoa.yeisson.movies_api.entities.Movie()));

        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));
        assertThrows(BadRequestException.class, () -> actorService.deleteActor(1L));
    }

    @Test
    void deleteActor_shouldThrowNotFound() {
        when(actorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> actorService.deleteActor(1L));
    }
}

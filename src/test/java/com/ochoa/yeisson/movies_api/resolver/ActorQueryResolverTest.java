package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.ActorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorQueryResolverTest {
    @Mock
    private ActorService actorService;
    @InjectMocks
    private ActorQueryResolver resolver;

    // ========================= // GET ALL // =========================
    @Test
    void actors_shouldReturnList() {
        List<ActorDTO> actors = List.of(new ActorDTO(), new ActorDTO());

        when(actorService.getAllActors()).thenReturn(actors);

        List<ActorDTO> result = resolver.actors();

        assertEquals(2, result.size());
        verify(actorService).getAllActors();
    }

    // ========================= // GET BY ID // =========================
    @Test
    void actorById_shouldReturnActor() {
        ActorDTO dto = new ActorDTO();

        when(actorService.getActorById(1L)).thenReturn(dto);

        ActorDTO result = resolver.actorById(1L);

        assertNotNull(result);
        verify(actorService).getActorById(1L);
    }

    @Test
    void actorById_shouldThrowNotFound() {
        when(actorService.getActorById(1L)) .thenThrow(new NotFoundException("Actor not found"));
        assertThrows(NotFoundException.class, () -> resolver.actorById(1L));
        verify(actorService).getActorById(1L);
    }
}

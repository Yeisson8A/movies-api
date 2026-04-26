package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.ActorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorMutationResolverTest {
    @Mock
    private ActorService actorService;
    @InjectMocks
    private ActorMutationResolver resolver;

    // ========================= // CREATE // =========================
    @Test
    void createActor_shouldReturnActor() {
        ActorDTO dto = new ActorDTO();

        when(actorService.createActor("Leo")).thenReturn(dto);

        ActorDTO result = resolver.createActor("Leo");

        assertNotNull(result);
        verify(actorService).createActor("Leo");
    }

    @Test
    void createActor_shouldThrowBadRequest() {
        when(actorService.createActor("")) .thenThrow(new BadRequestException("Invalid name"));
        assertThrows(BadRequestException.class, () -> resolver.createActor(""));
        verify(actorService).createActor("");
    }

    // ========================= // UPDATE // =========================
    @Test
    void updateActor_shouldReturnActor() {
        ActorDTO dto = new ActorDTO();

        when(actorService.updateActor(1L, "Brad")).thenReturn(dto);

        ActorDTO result = resolver.updateActor(1L, "Brad");

        assertNotNull(result);
        verify(actorService).updateActor(1L, "Brad");
    }

    @Test
    void updateActor_shouldThrowNotFound() {
        when(actorService.updateActor(1L, "Test")) .thenThrow(new NotFoundException("Actor not found"));
        assertThrows(NotFoundException.class, () -> resolver.updateActor(1L, "Test"));
        verify(actorService).updateActor(1L, "Test");
    }

    // ========================= // DELETE // =========================
    @Test
    void deleteActor_shouldReturnTrue() {
        doNothing().when(actorService).deleteActor(1L);

        Boolean result = resolver.deleteActor(1L);

        assertTrue(result);
        verify(actorService).deleteActor(1L);
    }

    @Test
    void deleteActor_shouldThrowNotFound() {
        doThrow(new NotFoundException("Actor not found")) .when(actorService).deleteActor(1L);
        assertThrows(NotFoundException.class, () -> resolver.deleteActor(1L));
        verify(actorService).deleteActor(1L);
    }
}

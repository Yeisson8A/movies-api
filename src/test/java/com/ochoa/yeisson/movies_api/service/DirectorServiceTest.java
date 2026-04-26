package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.entities.Director;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.mapper.DirectorMapper;
import com.ochoa.yeisson.movies_api.repository.DirectorRepository;
import com.ochoa.yeisson.movies_api.service.impl.DirectorServiceImpl;
import com.ochoa.yeisson.movies_api.validation.DirectorValidator;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {
    @Mock
    private DirectorRepository directorRepository;
    @Mock private DirectorMapper directorMapper;
    @Mock private DirectorValidator directorValidator;
    @InjectMocks
    private DirectorServiceImpl directorService;

    // ========================= // GET ALL // =========================
    @Test
    void getAllDirectors_shouldReturnList() {
        List<Director> directors = List.of(new Director(), new Director());
        List<DirectorDTO> dtos = List.of(new DirectorDTO(), new DirectorDTO());

        when(directorRepository.findAll()).thenReturn(directors);
        when(directorMapper.toDTOList(directors)).thenReturn(dtos);

        List<DirectorDTO> result = directorService.getAllDirectors();

        assertEquals(2, result.size());
        verify(directorRepository).findAll();
        verify(directorMapper).toDTOList(directors);
    }

    // ========================= // GET BY ID // =========================
    @Test
    void getDirectorById_shouldReturnDirector() {
        Director director = new Director();
        DirectorDTO dto = new DirectorDTO();

        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        when(directorMapper.toDTO(director)).thenReturn(dto);

        DirectorDTO result = directorService.getDirectorById(1L);

        assertNotNull(result); verify(directorRepository).findById(1L);
    }

    @Test
    void getDirectorById_shouldThrowNotFound() {
        when(directorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> directorService.getDirectorById(1L));
    }

    // ========================= // CREATE // =========================
    @Test
    void createDirector_shouldSaveDirector() {
        Director director = Director.builder().name("Nolan").build();
        Director saved = Director.builder().name("Nolan").build();
        DirectorDTO dto = new DirectorDTO();

        when(directorRepository.findByNameIgnoreCase("Nolan")) .thenReturn(Optional.empty());
        when(directorRepository.save(any(Director.class))).thenReturn(saved);
        when(directorMapper.toDTO(saved)).thenReturn(dto);

        DirectorDTO result = directorService.createDirector("Nolan");

        assertNotNull(result); verify(directorValidator).validateName("Nolan");
        verify(directorRepository).findByNameIgnoreCase("Nolan");
        verify(directorRepository).save(any(Director.class));
    }

    @Test
    void createDirector_shouldThrowIfExists() {
        Director existing = new Director();

        when(directorRepository.findByNameIgnoreCase("Nolan")) .thenReturn(Optional.of(existing));
        assertThrows(BadRequestException.class, () -> directorService.createDirector("Nolan"));
        verify(directorValidator).validateName("Nolan");
        verify(directorRepository).findByNameIgnoreCase("Nolan");
        verify(directorRepository, never()).save(any());
    }

    // ========================= // UPDATE // =========================
    @Test
    void updateDirector_shouldUpdateName() {
        Director director = new Director();
        DirectorDTO dto = new DirectorDTO();

        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        when(directorRepository.save(director)).thenReturn(director);
        when(directorMapper.toDTO(director)).thenReturn(dto);

        DirectorDTO result = directorService.updateDirector(1L, "Spielberg");

        assertNotNull(result); verify(directorValidator).validateName("Spielberg");
        verify(directorRepository).save(director);
    }

    @Test
    void updateDirector_shouldThrowNotFound() {
        when(directorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> directorService.updateDirector(1L, "Test"));
    }

    // ========================= // DELETE // =========================
    @Test
    void deleteDirector_shouldDelete() {
        Director director = new Director();
        director.setMovies(Collections.emptyList());

        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));

        directorService.deleteDirector(1L);

        verify(directorRepository).delete(director);
    }

    @Test
    void deleteDirector_shouldThrowIfHasMovies() {
        Director director = new Director();
        director.setMovies(List.of(new com.ochoa.yeisson.movies_api.entities.Movie()));

        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        assertThrows(BadRequestException.class, () -> directorService.deleteDirector(1L));
    }

    @Test
    void deleteDirector_shouldThrowNotFound() {
        when(directorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> directorService.deleteDirector(1L));
    }

    @Test
    void createDirector_shouldCallValidator_andSave() {
        String name = "Nolan";

        Director saved = Director.builder().name("Nolan").build();
        DirectorDTO dto = new DirectorDTO();

        // validator no lanza excepción
        doNothing().when(directorValidator).validateName(name);

        when(directorRepository.findByNameIgnoreCase(name))
                .thenReturn(Optional.empty());
        when(directorRepository.save(any(Director.class))).thenReturn(saved);
        when(directorMapper.toDTO(saved)).thenReturn(dto);

        DirectorDTO result = directorService.createDirector(name);

        assertNotNull(result);

        verify(directorValidator).validateName(name);
        verify(directorRepository).save(any(Director.class));
    }

    @Test
    void createDirector_shouldThrow_whenValidatorFails() {
        String name = "";

        doThrow(new BadRequestException("Invalid name"))
                .when(directorValidator).validateName(name);

        assertThrows(BadRequestException.class,
                () -> directorService.createDirector(name));

        verify(directorValidator).validateName(name);
        verify(directorRepository, never()).save(any());
    }

    @Test
    void updateDirector_shouldCallValidator_andUpdate() {
        Director director = new Director();
        DirectorDTO dto = new DirectorDTO();

        when(directorRepository.findById(1L))
                .thenReturn(Optional.of(director));

        doNothing().when(directorValidator).validateName("Spielberg");

        when(directorRepository.save(director)).thenReturn(director);
        when(directorMapper.toDTO(director)).thenReturn(dto);

        DirectorDTO result = directorService.updateDirector(1L, "Spielberg");

        assertNotNull(result);

        verify(directorValidator).validateName("Spielberg");
        verify(directorRepository).save(director);
    }

    @Test
    void updateDirector_shouldThrow_whenValidatorFails() {
        Director director = new Director();

        when(directorRepository.findById(1L))
                .thenReturn(Optional.of(director));

        doThrow(new BadRequestException("Invalid name"))
                .when(directorValidator).validateName("");

        assertThrows(BadRequestException.class,
                () -> directorService.updateDirector(1L, ""));

        verify(directorValidator).validateName("");
        verify(directorRepository, never()).save(any());
    }
}

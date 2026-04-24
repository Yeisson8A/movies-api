package com.ochoa.yeisson.movies_api.service.impl;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.entities.Director;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.mapper.DirectorMapper;
import com.ochoa.yeisson.movies_api.repository.DirectorRepository;
import com.ochoa.yeisson.movies_api.service.DirectorService;
import com.ochoa.yeisson.movies_api.validation.DirectorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;
    private final DirectorValidator directorValidator;

    @Override
    public List<DirectorDTO> getAllDirectors() {
        return directorMapper.toDTOList(directorRepository.findAll());
    }

    @Override
    public DirectorDTO getDirectorById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director not found"));

        return directorMapper.toDTO(director);
    }

    @Override
    public DirectorDTO createDirector(String name) {
        directorValidator.validateName(name);

        // evitar duplicados
        directorRepository.findByNameIgnoreCase(name)
                .ifPresent(d -> {
                    throw new BadRequestException("Director already exists");
                });

        Director director = Director.builder()
                .name(name.trim())
                .build();

        Director saved = directorRepository.save(director);

        return directorMapper.toDTO(saved);
    }

    @Override
    public DirectorDTO updateDirector(Long id, String name) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director not found"));

        directorValidator.validateName(name);

        director.setName(name.trim());

        return directorMapper.toDTO(directorRepository.save(director));
    }

    @Override
    public void deleteDirector(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director not found"));

        // evitar borrar si tiene películas
        if (director.getMovies() != null && !director.getMovies().isEmpty()) {
            throw new BadRequestException("Cannot delete director with movies");
        }

        directorRepository.delete(director);
    }
}

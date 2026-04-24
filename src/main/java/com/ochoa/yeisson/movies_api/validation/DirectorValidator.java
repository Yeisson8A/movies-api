package com.ochoa.yeisson.movies_api.validation;

import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectorValidator {
    private final DirectorRepository directorRepository;

    public void validateName(String name) {
        ValidationUtils.requireNonBlank(name, "Director name");

        directorRepository.findByNameIgnoreCase(name)
                .ifPresent(d -> {
                    throw new BadRequestException("Director already exists");
                });
    }
}

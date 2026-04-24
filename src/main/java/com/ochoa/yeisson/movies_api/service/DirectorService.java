package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import java.util.List;

public interface DirectorService {
    List<DirectorDTO> getAllDirectors();

    DirectorDTO getDirectorById(Long id);

    DirectorDTO createDirector(String name);

    DirectorDTO updateDirector(Long id, String name);

    void deleteDirector(Long id);
}

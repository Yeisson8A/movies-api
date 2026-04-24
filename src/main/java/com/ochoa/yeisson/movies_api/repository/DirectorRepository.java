package com.ochoa.yeisson.movies_api.repository;

import com.ochoa.yeisson.movies_api.entities.Director;
import java.util.Optional;

public interface DirectorRepository extends BaseRepository<Director, Long> {
    Optional<Director> findByNameIgnoreCase(String name);
}

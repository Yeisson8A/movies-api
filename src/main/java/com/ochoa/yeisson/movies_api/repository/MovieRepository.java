package com.ochoa.yeisson.movies_api.repository;

import com.ochoa.yeisson.movies_api.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
import java.util.List;

public interface MovieRepository extends BaseRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByReleaseYear(Integer year);

    Page<Movie> findAll(Pageable pageable);

    @Query("""
        SELECT m FROM Movie m
        LEFT JOIN FETCH m.actors
        WHERE m.id IN :movieIds
    """)
    List<Movie> findMoviesWithActors(@Param("movieIds") Collection<Long> movieIds);
}

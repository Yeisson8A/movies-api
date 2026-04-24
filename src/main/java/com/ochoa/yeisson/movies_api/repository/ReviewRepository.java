package com.ochoa.yeisson.movies_api.repository;

import com.ochoa.yeisson.movies_api.entities.Review;
import java.util.List;

public interface ReviewRepository extends BaseRepository<Review, Long> {
    List<Review> findByMovie_Id(Long movieId);

    List<Review> findByMovie_IdIn(List<Long> movieIds);
}

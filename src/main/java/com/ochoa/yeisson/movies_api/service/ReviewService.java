package com.ochoa.yeisson.movies_api.service;

import com.ochoa.yeisson.movies_api.dto.ReviewDTO;
import com.ochoa.yeisson.movies_api.dto.ReviewInput;

public interface ReviewService {
    ReviewDTO addReview(Long movieId, ReviewInput input);
}

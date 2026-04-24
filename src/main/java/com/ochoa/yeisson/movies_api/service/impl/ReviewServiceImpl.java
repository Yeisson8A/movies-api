package com.ochoa.yeisson.movies_api.service.impl;

import com.ochoa.yeisson.movies_api.dto.ReviewDTO;
import com.ochoa.yeisson.movies_api.dto.ReviewInput;
import com.ochoa.yeisson.movies_api.entities.Movie;
import com.ochoa.yeisson.movies_api.entities.Review;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.mapper.ReviewInputMapper;
import com.ochoa.yeisson.movies_api.mapper.ReviewMapper;
import com.ochoa.yeisson.movies_api.repository.MovieRepository;
import com.ochoa.yeisson.movies_api.repository.ReviewRepository;
import com.ochoa.yeisson.movies_api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final ReviewInputMapper reviewInputMapper;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO addReview(Long movieId, ReviewInput input) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        if (input.getRating() == null || input.getRating() < 1 || input.getRating() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        Review review = reviewInputMapper.toEntity(input);
        review.setMovie(movie);

        Review saved = reviewRepository.save(review);

        return reviewMapper.toDTO(saved);
    }
}

package com.ochoa.yeisson.movies_api.service;

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
import com.ochoa.yeisson.movies_api.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private ReviewInputMapper reviewInputMapper;
    @Mock private ReviewMapper reviewMapper;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    // ========================= // SUCCESS CASE // =========================
    @Test
    void addReview_shouldSaveReview() {
        Long movieId = 1L;
        Movie movie = new Movie();
        ReviewInput input = new ReviewInput();
        input.setRating(5);
        Review review = new Review();
        Review saved = new Review();
        ReviewDTO dto = new ReviewDTO();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(reviewInputMapper.toEntity(input)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(saved);
        when(reviewMapper.toDTO(saved)).thenReturn(dto);

        ReviewDTO result = reviewService.addReview(movieId, input);

        assertNotNull(result); verify(movieRepository).findById(movieId);
        verify(reviewInputMapper).toEntity(input);
        verify(reviewRepository).save(review);
        verify(reviewMapper).toDTO(saved);
        assertEquals(movie, review.getMovie());
    }

    // ========================= // MOVIE NOT FOUND // =========================
    @Test
    void addReview_shouldThrowNotFound_whenMovieNotExists() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        ReviewInput input = new ReviewInput();
        input.setRating(5);

        assertThrows(NotFoundException.class, () -> reviewService.addReview(1L, input));
        verify(reviewRepository, never()).save(any());
    }

    // ========================= // INVALID RATING - NULL // =========================
    @Test
    void addReview_shouldThrowBadRequest_whenRatingIsNull() {
        Movie movie = new Movie();
        ReviewInput input = new ReviewInput();
        input.setRating(null);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        assertThrows(BadRequestException.class, () -> reviewService.addReview(1L, input));
        verify(reviewRepository, never()).save(any());
    }

    // ========================= // INVALID RATING - < 1 // =========================
    @Test
    void addReview_shouldThrowBadRequest_whenRatingLessThanOne() {
        Movie movie = new Movie();
        ReviewInput input = new ReviewInput();
        input.setRating(0);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        assertThrows(BadRequestException.class, () -> reviewService.addReview(1L, input));
        verify(reviewRepository, never()).save(any());
    }

    // ========================= // INVALID RATING - > 5 // =========================
    @Test
    void addReview_shouldThrowBadRequest_whenRatingGreaterThanFive() {
        Movie movie = new Movie();
        ReviewInput input = new ReviewInput();
        input.setRating(6);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        assertThrows(BadRequestException.class, () -> reviewService.addReview(1L, input));
        verify(reviewRepository, never()).save(any());
    }
}

package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.ReviewDTO;
import com.ochoa.yeisson.movies_api.dto.ReviewInput;
import com.ochoa.yeisson.movies_api.exception.BadRequestException;
import com.ochoa.yeisson.movies_api.exception.NotFoundException;
import com.ochoa.yeisson.movies_api.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewMutationResolverTest {
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private ReviewMutationResolver resolver;

    // ========================= // SUCCESS // =========================
    @Test
    void addReview_shouldReturnReview() {
        ReviewInput input = new ReviewInput();
        input.setRating(5);
        ReviewDTO dto = new ReviewDTO();

        when(reviewService.addReview(1L, input)).thenReturn(dto);

        ReviewDTO result = resolver.addReview(1L, input);

        assertNotNull(result);
        verify(reviewService).addReview(1L, input);
    }

    // ========================= // MOVIE NOT FOUND // =========================
    @Test
    void addReview_shouldThrowNotFound() {
        ReviewInput input = new ReviewInput();
        input.setRating(5);

        when(reviewService.addReview(1L, input)) .thenThrow(new NotFoundException("Movie not found"));
        assertThrows(NotFoundException.class, () -> resolver.addReview(1L, input));
        verify(reviewService).addReview(1L, input);
    }

    // ========================= // INVALID RATING // =========================
    @Test
    void addReview_shouldThrowBadRequest() {
        ReviewInput input = new ReviewInput();
        input.setRating(10);

        when(reviewService.addReview(1L, input)) .thenThrow(new BadRequestException("Invalid rating"));
        assertThrows(BadRequestException.class, () -> resolver.addReview(1L, input));
        verify(reviewService).addReview(1L, input);
    }
}

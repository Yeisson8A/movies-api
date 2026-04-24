package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.ReviewDTO;
import com.ochoa.yeisson.movies_api.dto.ReviewInput;
import com.ochoa.yeisson.movies_api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReviewMutationResolver {
    private final ReviewService reviewService;

    @MutationMapping
    public ReviewDTO addReview(@Argument Long movieId, @Argument ReviewInput input) {
        return reviewService.addReview(movieId, input);
    }
}

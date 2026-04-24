package com.ochoa.yeisson.movies_api.mapper;

import com.ochoa.yeisson.movies_api.dto.ReviewDTO;
import com.ochoa.yeisson.movies_api.entities.Review;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDTO toDTO(Review review);

    Review toEntity(ReviewDTO dto);

    List<ReviewDTO> toDTOList(List<Review> reviews);
}

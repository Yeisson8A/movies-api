package com.ochoa.yeisson.movies_api.mapper;

import com.ochoa.yeisson.movies_api.dto.ReviewInput;
import com.ochoa.yeisson.movies_api.entities.Review;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface ReviewInputMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true) // se asigna en el service
    Review toEntity(ReviewInput input);
}

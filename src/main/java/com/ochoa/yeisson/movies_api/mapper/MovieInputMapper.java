package com.ochoa.yeisson.movies_api.mapper;

import com.ochoa.yeisson.movies_api.dto.MovieInput;
import com.ochoa.yeisson.movies_api.entities.Movie;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface MovieInputMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "director", ignore = true) // se setea en service
    @Mapping(target = "actors", ignore = true)   // se setea en service
    @Mapping(target = "reviews", ignore = true)
    Movie toEntity(MovieInput input);
}

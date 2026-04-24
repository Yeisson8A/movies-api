package com.ochoa.yeisson.movies_api.mapper;

import com.ochoa.yeisson.movies_api.dto.MovieDTO;
import com.ochoa.yeisson.movies_api.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ActorMapper.class, DirectorMapper.class, ReviewMapper.class}
)
public interface MovieMapper {
    @Mapping(target = "director", source = "director")
    @Mapping(target = "actors", source = "actors")
    @Mapping(target = "reviews", source = "reviews")
    MovieDTO toDTO(Movie movie);
}

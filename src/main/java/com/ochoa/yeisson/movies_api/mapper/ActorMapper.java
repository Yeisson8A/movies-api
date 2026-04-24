package com.ochoa.yeisson.movies_api.mapper;

import com.ochoa.yeisson.movies_api.dto.ActorDTO;
import com.ochoa.yeisson.movies_api.entities.Actor;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorDTO toDTO(Actor actor);

    Actor toEntity(ActorDTO dto);

    List<ActorDTO> toDTOList(List<Actor> actores);
}

package com.ochoa.yeisson.movies_api.mapper;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.entities.Director;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorDTO toDTO(Director director);

    Director toEntity(DirectorDTO dto);

    List<DirectorDTO> toDTOList(List<Director> directores);
}

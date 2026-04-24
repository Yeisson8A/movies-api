package com.ochoa.yeisson.movies_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectorDTO {
    private Long id;
    private String name;
}

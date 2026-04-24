package com.ochoa.yeisson.movies_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieInput {
    private String title;
    private Integer releaseYear;
    private Long directorId;
    private List<Long> actorIds;
}

package com.ochoa.yeisson.movies_api.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MovieDTO {
    private Long id;
    private String title;
    private Integer releaseYear;

    private DirectorDTO director;
    private List<ActorDTO> actors;
    private List<ReviewDTO> reviews;
}

package com.ochoa.yeisson.movies_api.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String comment;
    private Integer rating;
    private String username;
    private LocalDateTime createdAt;
}

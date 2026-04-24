package com.ochoa.yeisson.movies_api.dto;

import lombok.Data;

@Data
public class ReviewInput {
    private String comment;
    private Integer rating;
    private String username;
}

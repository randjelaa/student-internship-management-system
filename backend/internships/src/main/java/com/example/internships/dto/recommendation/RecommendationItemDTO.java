package com.example.internships.dto.recommendation;

import lombok.Data;

@Data
public class RecommendationItemDTO {

    private Integer internshipIndex;
    private Double score;
    private String explanation;
}
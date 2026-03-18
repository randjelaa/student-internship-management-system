package com.example.internships.dto.recommendation;

import lombok.Data;

@Data
public class RecommendationResponseDTO {

    private Long internshipId;
    private String internshipTitle;
    private Double score;
    private String explanation;
}
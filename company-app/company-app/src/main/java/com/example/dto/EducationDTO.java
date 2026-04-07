package com.example.dto;

import lombok.Data;

@Data
public class EducationDTO {
    private Long id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
}
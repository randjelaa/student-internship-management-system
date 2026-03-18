package com.example.internships.dto.cv;

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
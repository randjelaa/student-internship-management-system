package com.example.dto;

import lombok.Data;

@Data
public class ExperienceDTO {
    private Long id;
    private String companyName;
    private String position;
    private String description;
    private String startDate;
    private String endDate;
}

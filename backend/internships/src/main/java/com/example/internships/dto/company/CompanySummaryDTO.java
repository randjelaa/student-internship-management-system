package com.example.internships.dto.company;

import lombok.Data;

@Data
public class CompanySummaryDTO {
    private Long id;
    private String name;
    private Boolean active;
}
package com.example.internships.dto.internship;

import lombok.Data;

@Data
public class InternshipSummaryDTO {
    private Long id;
    private String title;
    private String companyName;
    private String location;
}
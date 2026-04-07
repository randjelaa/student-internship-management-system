package com.example.dto;

import lombok.Data;

@Data
public class WorkLogResponseDTO {
    private Long id;
    private Long studentId;
    private Long internshipId;
    private String startDate;
    private String endDate;
    private String description;
}
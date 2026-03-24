package com.example.internships.dto.worklog;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkLogResponseDTO {
    private Long id;
    private Long studentId;
    private Long internshipId;
    private LocalDate startDate; // Promenjeno
    private LocalDate endDate;   // Promenjeno
    private String description;
}
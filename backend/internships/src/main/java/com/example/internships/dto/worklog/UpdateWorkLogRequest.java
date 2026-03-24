package com.example.internships.dto.worklog;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateWorkLogRequest {
    private LocalDate startDate; // Promenjeno
    private LocalDate endDate;   // Promenjeno
    private String description;
}
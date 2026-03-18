package com.example.internships.dto.worklog;

import lombok.Data;

@Data
public class CreateWorkLogRequest {
    private Long studentId;
    private Long internshipId;
    private Integer weekNumber;
    private String description;
}
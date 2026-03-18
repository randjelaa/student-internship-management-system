package com.example.internships.dto.worklog;

import lombok.Data;

@Data
public class UpdateWorkLogRequest {
    private Integer weekNumber;
    private String description;
}
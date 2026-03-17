package com.example.internships.dto.application;

import lombok.Data;

import java.time.Instant;

@Data
public class ApplicationResponseDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long internshipId;
    private String internshipTitle;
    private String status;
    private Instant appliedAt;
}
package com.example.internships.dto.specific;

import lombok.Data;

import java.time.Instant;

@Data
public class CompanyApplicationViewDTO {
    private Long applicationId;
    private String studentFullName;
    private String internshipTitle;
    private String status;
    private Instant appliedAt;
    private Long studentId;
}

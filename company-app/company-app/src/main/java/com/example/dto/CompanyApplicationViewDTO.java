package com.example.dto;

import lombok.Data;

@Data
public class CompanyApplicationViewDTO {
    private Long applicationId;
    private String studentFullName;
    private String internshipTitle;
    private String status;
    private String appliedAt;
    private Long studentId;
}

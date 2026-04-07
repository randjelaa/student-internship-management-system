package com.example.dto;

import lombok.Data;

@Data
public class CreateGradeRequest {
    private Long studentId;
    private Long internshipId;
    private String companyComment;
    private Integer facultyGrade;
}

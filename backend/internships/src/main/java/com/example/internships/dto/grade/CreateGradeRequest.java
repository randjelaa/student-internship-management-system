package com.example.internships.dto.grade;

import lombok.Data;

@Data
public class CreateGradeRequest {
    private Long studentId;
    private Long internshipId;
    private String companyComment;
    private Integer facultyGrade;
}
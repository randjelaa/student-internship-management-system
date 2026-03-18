package com.example.internships.dto.grade;

import lombok.Data;

@Data
public class GradeResponseDTO {
    private Long id;
    private Long studentId;
    private Long internshipId;
    private String companyComment;
    private Integer facultyGrade;
}
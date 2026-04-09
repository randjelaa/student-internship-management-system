package com.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class GradeDetails {
    private Long id;
    private StudentSummaryDTO student;
    private InternshipSummaryDTO internship;
    private String companyComment;
    private Integer facultyGrade;
    private List<WorkLogResponseDTO> workLogs;
}
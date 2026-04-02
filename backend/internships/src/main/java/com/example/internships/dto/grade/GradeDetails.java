package com.example.internships.dto.grade;

import com.example.internships.dto.internship.InternshipSummaryDTO;
import com.example.internships.dto.student.StudentSummaryDTO;
import com.example.internships.dto.worklog.WorkLogResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GradeDetails {

    private Long id;
    private StudentSummaryDTO student;
    private InternshipSummaryDTO internship;
    private String companyComment;
    private Integer facultyGrade;
    private List<WorkLogResponseDTO> workLogs;
}

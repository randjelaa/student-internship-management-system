package com.example.internships.dto.specific;

import com.example.internships.dto.worklog.WorkLogResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class StudentGradingDetailDTO {
    private Long studentId;
    private String studentFullName;
    private List<WorkLogResponseDTO> workLogs;
    private String existingComment;
    private boolean isGraded;
}

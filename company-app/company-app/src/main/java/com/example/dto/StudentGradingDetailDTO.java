package com.example.dto;

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

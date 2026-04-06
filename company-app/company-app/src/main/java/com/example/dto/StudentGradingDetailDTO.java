package com.example.dto;

import java.util.List;

public class StudentGradingDetailDTO {
    private Long studentId;
    private String studentFullName;
    private List<WorkLogResponseDTO> workLogs;
    private String existingComment;
    private boolean isGraded;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public List<WorkLogResponseDTO> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLogResponseDTO> workLogs) {
        this.workLogs = workLogs;
    }

    public String getExistingComment() {
        return existingComment;
    }

    public void setExistingComment(String existingComment) {
        this.existingComment = existingComment;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }
}

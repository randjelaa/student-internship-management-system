package com.example.dto;

import java.util.List;

public class InternshipGradingGroupDTO {
    private Long internshipId;
    private String internshipTitle;
    private List<StudentGradingDetailDTO> students;

    public Long getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Long internshipId) {
        this.internshipId = internshipId;
    }

    public String getInternshipTitle() {
        return internshipTitle;
    }

    public void setInternshipTitle(String internshipTitle) {
        this.internshipTitle = internshipTitle;
    }

    public List<StudentGradingDetailDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentGradingDetailDTO> students) {
        this.students = students;
    }
}

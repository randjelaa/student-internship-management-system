package com.example.dto;

import java.util.List;

public class GradeDetails {

    private Long id;
    private StudentSummaryDTO student;
    private InternshipSummaryDTO internship;
    private String companyComment;
    private Integer facultyGrade;
    private List<WorkLogResponseDTO> workLogs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentSummaryDTO getStudent() {
        return student;
    }

    public void setStudent(StudentSummaryDTO student) {
        this.student = student;
    }

    public InternshipSummaryDTO getInternship() {
        return internship;
    }

    public void setInternship(InternshipSummaryDTO internship) {
        this.internship = internship;
    }

    public String getCompanyComment() {
        return companyComment;
    }

    public void setCompanyComment(String companyComment) {
        this.companyComment = companyComment;
    }

    public Integer getFacultyGrade() {
        return facultyGrade;
    }

    public void setFacultyGrade(Integer facultyGrade) {
        this.facultyGrade = facultyGrade;
    }

    public List<WorkLogResponseDTO> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLogResponseDTO> workLogs) {
        this.workLogs = workLogs;
    }
}
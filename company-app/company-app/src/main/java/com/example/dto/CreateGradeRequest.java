package com.example.dto;

public class CreateGradeRequest {
    private Long studentId;
    private Long internshipId;
    private String companyComment;
    private Integer facultyGrade;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Long internshipId) {
        this.internshipId = internshipId;
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
}

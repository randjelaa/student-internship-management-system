package com.example.dto;

import java.util.List;

public class InternshipApplicationsGroupDTO {
    private Long internshipId;
    private String internshipTitle;
    private List<CompanyApplicationViewDTO> applications;

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

    public List<CompanyApplicationViewDTO> getApplications() {
        return applications;
    }

    public void setApplications(List<CompanyApplicationViewDTO> applications) {
        this.applications = applications;
    }
}

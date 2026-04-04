package com.example.internships.dto.specific;

import lombok.Data;

import java.util.List;

@Data
public class InternshipApplicationsGroupDTO {
    private Long internshipId;
    private String internshipTitle;
    private List<CompanyApplicationViewDTO> applications;
}
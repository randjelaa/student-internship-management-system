package com.example.internships.dto.specific;

import lombok.Data;

import java.util.List;

@Data
public class InternshipGradingGroupDTO {
    private Long internshipId;
    private String internshipTitle;
    private List<StudentGradingDetailDTO> students;
}
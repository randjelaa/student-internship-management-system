package com.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class InternshipGradingGroupDTO {
    private Long internshipId;
    private String internshipTitle;
    private List<StudentGradingDetailDTO> students;
}

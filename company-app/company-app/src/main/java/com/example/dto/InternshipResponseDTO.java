package com.example.dto;

import lombok.Data;
import java.util.Set;

@Data
public class InternshipResponseDTO {
    private Long id;
    private Long companyId;
    private String companyName;
    private String title;
    private String description;
    private String location;
    private String startDate;
    private String endDate;
    private String requirements;
    private Set<String> technologies;
}
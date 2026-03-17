package com.example.internships.dto.internship;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class InternshipResponseDTO {
    private Long id;
    private Long companyId;
    private String companyName;
    private String title;
    private String description;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String requirements;
    private Set<String> technologies;
}
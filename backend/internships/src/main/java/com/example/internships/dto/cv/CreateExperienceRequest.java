package com.example.internships.dto.cv;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateExperienceRequest {
    private String companyName;
    private String position;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
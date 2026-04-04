package com.example.internships.dto.internship;

import com.example.internships.dto.application.ApplicationResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class InternshipApplicationsDTO {
    private InternshipResponseDTO internship;
    private List<ApplicationResponseDTO> applications;
}

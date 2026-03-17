package com.example.internships.dto.company;

import lombok.Data;

@Data
public class CompanyResponseDTO {
    private Long id;
    private Long userId;
    private String email;
    private String name;
    private String description;
    private String website;
    private Boolean active;
}
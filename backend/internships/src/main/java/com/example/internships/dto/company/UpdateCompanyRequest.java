package com.example.internships.dto.company;

import lombok.Data;

@Data
public class UpdateCompanyRequest {
    private String email;
    private String password;
    private String name;
    private String description;
    private String website;
}
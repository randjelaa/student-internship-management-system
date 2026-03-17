package com.example.internships.dto.application;

import lombok.Data;

@Data
public class CreateApplicationRequest {
    private Long studentId;
    private Long internshipId;
}
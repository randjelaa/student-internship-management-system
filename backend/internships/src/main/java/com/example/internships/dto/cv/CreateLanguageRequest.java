package com.example.internships.dto.cv;

import lombok.Data;

@Data
public class CreateLanguageRequest {
    private String languageName;
    private String level;
}
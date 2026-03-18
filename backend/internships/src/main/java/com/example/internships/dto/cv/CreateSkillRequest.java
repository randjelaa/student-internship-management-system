package com.example.internships.dto.cv;

import lombok.Data;

@Data
public class CreateSkillRequest {
    private String skillName;
    private String skillLevel;
}
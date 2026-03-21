package com.example.internships.dto.cv;

import lombok.Data;

import java.util.Set;

@Data
public class CreateCvRequest {

    private String photoUrl;
    private String summary;

    // Koristimo DTO-ove koji mogu imati ID (za update) ili biti bez njega (za create)
    private Set<EducationDTO> educations;
    private Set<ExperienceDTO> experiences;
    private Set<SkillDTO> skills;
    private Set<LanguageDTO> languages;
    private Set<InterestDTO> interests;
}
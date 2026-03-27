package com.example.internships.dto.cv;

import lombok.Data;
import java.util.Set;

@Data
public class CreateCvRequest {
    private String photoUrl;
    private String summary;

    private Set<EducationDTO> educations;
    private Set<ExperienceDTO> experiences;
    private Set<SkillDTO> skills;
    private Set<LanguageDTO> languages;
    private Set<InterestDTO> interests;
}
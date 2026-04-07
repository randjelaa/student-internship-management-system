package com.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class CvResponseDTO {

    private Long id;
    private String photoUrl;
    private String summary;
    private String createdAt;
    private String updatedAt;

    private List<EducationDTO> educations;
    private List<ExperienceDTO> experiences;
    private List<SkillDTO> skills;
    private List<LanguageDTO> languages;
    private List<InterestDTO> interests;
}
package com.example.internships.dto.cv;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class CvResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String summary;
    private Instant createdAt;
    private Instant updatedAt;

    private List<EducationDTO> educations;
    private List<ExperienceDTO> experiences;
    private List<SkillDTO> skills;
    private List<LanguageDTO> languages;
    private List<InterestDTO> interests;
}
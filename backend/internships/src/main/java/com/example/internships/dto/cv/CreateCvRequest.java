package com.example.internships.dto.cv;

import lombok.Data;

import java.util.Set;

@Data
public class CreateCvRequest {

    private String photoUrl;
    private String summary;

    // postojeći ID-evi
    private Set<Long> educationIds;
    private Set<Long> experienceIds;
    private Set<Long> skillIds;
    private Set<Long> languageIds;
    private Set<Long> interestIds;

    // novi objekti
    private Set<CreateEducationRequest> newEducations;
    private Set<CreateExperienceRequest> newExperiences;
    private Set<CreateSkillRequest> newSkills;
    private Set<CreateLanguageRequest> newLanguages;
    private Set<CreateInterestRequest> newInterests;
}
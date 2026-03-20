package com.example.internships.mapper;

import com.example.internships.dto.cv.*;
import com.example.internships.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CvMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "educations", ignore = true)
    @Mapping(target = "experiences", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "interests", ignore = true)
    Cv toEntity(CreateCvRequest request);

    @Mapping(target = "educations", source = "educations")
    @Mapping(target = "experiences", source = "experiences")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "languages", source = "languages")
    @Mapping(target = "interests", source = "interests")
    CvResponseDTO toDto(Cv cv);

    List<EducationDTO> toEducationDtos(Set<Education> educations);
    List<ExperienceDTO> toExperienceDtos(Set<Experience> experiences);
    List<SkillDTO> toSkillDtos(Set<Skill> skills);
    List<LanguageDTO> toLanguageDtos(Set<Language> languages);
    List<InterestDTO> toInterestDtos(Set<Interest> interests);

    EducationDTO toDto(Education education);
    ExperienceDTO toDto(Experience experience);
    SkillDTO toDto(Skill skill);
    LanguageDTO toDto(Language language);
    InterestDTO toDto(Interest interest);
}

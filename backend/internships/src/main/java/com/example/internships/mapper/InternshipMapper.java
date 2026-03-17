package com.example.internships.mapper;

import com.example.internships.dto.internship.*;
import com.example.internships.entity.Internship;
import com.example.internships.entity.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InternshipMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "technologies", expression = "java(entity.getTechnologies().stream().map(com.example.internships.entity.Technology::getName).collect(java.util.stream.Collectors.toSet()))", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    @Mapping(target = "workLogs", ignore = true)
    Internship toEntity(CreateInternshipRequest dto);

    void updateFromDto(UpdateInternshipRequest dto, @MappingTarget Internship entity);

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    @Mapping(
            target = "technologies",
            expression = "java(entity.getTechnologies().stream().map(com.example.internships.entity.Technology::getName).collect(java.util.stream.Collectors.toSet()))"
    )
    InternshipResponseDTO toResponse(Internship entity);

    @Mapping(target = "companyName", source = "company.name")
    InternshipSummaryDTO toSummary(Internship entity);
}
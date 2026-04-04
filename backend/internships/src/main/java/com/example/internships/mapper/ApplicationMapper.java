package com.example.internships.mapper;

import com.example.internships.dto.application.ApplicationResponseDTO;
import com.example.internships.dto.specific.CompanyApplicationViewDTO;
import com.example.internships.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", expression = "java(application.getStudent().getFirstName() + \" \" + application.getStudent().getLastName())")
    @Mapping(target = "internshipId", source = "internship.id")
    @Mapping(target = "internshipTitle", source = "internship.title")
    ApplicationResponseDTO toResponse(Application application);

    @Mapping(target = "applicationId", source = "id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentFullName", expression = "java(application.getStudent().getFirstName() + \" \" + application.getStudent().getLastName())")
    @Mapping(target = "internshipTitle", source = "internship.title")
    CompanyApplicationViewDTO toViewDto(Application application);
}
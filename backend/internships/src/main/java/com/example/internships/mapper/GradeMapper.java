package com.example.internships.mapper;

import com.example.internships.dto.grade.*;
import com.example.internships.entity.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "internship", ignore = true)
    Grade toEntity(CreateGradeRequest request);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "internshipId", source = "internship.id")
    GradeResponseDTO toResponse(Grade grade);

}
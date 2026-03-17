package com.example.internships.mapper;

import com.example.internships.dto.student.CreateStudentRequest;
import com.example.internships.dto.student.StudentResponseDTO;
import com.example.internships.dto.student.StudentSummaryDTO;
import com.example.internships.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.internships.dto.student.UpdateStudentRequest;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    // CREATE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "cvs", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    @Mapping(target = "workLogs", ignore = true)
    Student toEntity(CreateStudentRequest request);

    // UPDATE 🔥
    void updateStudentFromDto(
            UpdateStudentRequest dto,
            @MappingTarget Student entity
    );

    // RESPONSE
    @Mapping(source = "user.id", target = "userId")
    StudentResponseDTO toResponse(Student student);

    StudentSummaryDTO toSummary(Student student);
}
package com.example.internships.mapper;

import com.example.internships.dto.worklog.*;
import com.example.internships.entity.WorkLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "internship", ignore = true)
    WorkLog toEntity(CreateWorkLogRequest request);

    void updateWorkLogFromDto(UpdateWorkLogRequest dto, @MappingTarget WorkLog entity);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "internshipId", source = "internship.id")
    WorkLogResponseDTO toResponse(WorkLog workLog);

}
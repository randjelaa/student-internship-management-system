package com.example.internships.mapper;

import com.example.internships.dto.company.*;
import com.example.internships.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "internships", ignore = true)
    Company toEntity(CreateCompanyRequest dto);

    void updateFromDto(UpdateCompanyRequest dto, @MappingTarget Company entity);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    CompanyResponseDTO toResponse(Company entity);

    CompanySummaryDTO toSummary(Company entity);
}
package com.example.internships.mapper;

import com.example.internships.dto.technology.CreateTechnologyDTO;
import com.example.internships.dto.technology.TechnologyResponseDTO;
import com.example.internships.entity.Technology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

    TechnologyResponseDTO toDto(Technology entity);
    Technology toEntity(CreateTechnologyDTO dto);
}
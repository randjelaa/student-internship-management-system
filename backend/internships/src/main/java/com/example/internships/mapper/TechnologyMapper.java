package com.example.internships.mapper;

import com.example.internships.dto.technology.TechnologyDTO;
import com.example.internships.entity.Technology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

    TechnologyDTO toDto(Technology entity);

}
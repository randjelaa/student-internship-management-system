package com.example.internships.controller;

import com.example.internships.dto.technology.TechnologyDTO;
import com.example.internships.entity.Technology;
import com.example.internships.mapper.TechnologyMapper;
import com.example.internships.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;
    private final TechnologyMapper technologyMapper;

    @GetMapping
    public List<TechnologyDTO> getAll() {
        return technologyService.getAllTechnologies()
                .stream()
                .map(technologyMapper::toDto)
                .toList();
    }

}

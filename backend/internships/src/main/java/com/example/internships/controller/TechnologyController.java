package com.example.internships.controller;

import com.example.internships.dto.technology.CreateTechnologyDTO;
import com.example.internships.dto.technology.TechnologyResponseDTO;
import com.example.internships.mapper.TechnologyMapper;
import com.example.internships.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;
    private final TechnologyMapper technologyMapper;

    @GetMapping
    public List<TechnologyResponseDTO> getAll() {
        return technologyService.getAllTechnologies()
                .stream()
                .map(technologyMapper::toDto)
                .toList();
    }

    @PostMapping
    public TechnologyResponseDTO create(@RequestBody CreateTechnologyDTO technologyDTO) {
        return technologyService.createTechnology(technologyDTO);
    }
}
